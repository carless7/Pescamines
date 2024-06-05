package com.example.pescamines.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pescamines.data.GameDao
import com.example.pescamines.data.UserPreferencesRepository
import com.example.pescamines.model.Board
import com.example.pescamines.model.BombManager
import com.example.pescamines.model.GameRecord
import com.example.pescamines.model.GameStatus
import com.example.pescamines.model.NumberCalculator
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class GameViewModel(
    private val gameDao: GameDao,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    // Configuración del juego
    val userName = MutableStateFlow("")
    val gridOption = MutableStateFlow(8)
    val timerEnabled = MutableStateFlow(true)
    val bombPercentage = MutableStateFlow(10)
    val timeRemaining = MutableStateFlow(240)
    var gameResult = MutableStateFlow(GameResult.InProgress)

    // Referencias al modelo de datos del juego
    lateinit var board: Board
    private lateinit var bombManager: BombManager
    private lateinit var numberCalculator: NumberCalculator

    private var timerJob: Job? = null  // Trabajo del temporizador

    init {
        viewModelScope.launch {
            userName.value = userPreferencesRepository.userNameFlow.first()
            gridOption.value = userPreferencesRepository.gridSizeFlow.first()
            timerEnabled.value = userPreferencesRepository.timerEnabledFlow.first()
            bombPercentage.value = userPreferencesRepository.bombPercentageFlow.first()
            initializeGame()
        }
    }

    // Inicializar el juego con la configuración actual
    private fun initializeGame() {
        bombManager = BombManager((gridOption.value * gridOption.value * bombPercentage.value / 100))
        numberCalculator = NumberCalculator()
        board = Board(gridOption.value)
        board.initialize(bombManager, numberCalculator)
        if (timerEnabled.value) {
            startTimer()
        }
        gameResult.value = GameResult.InProgress
    }

    // Manejar clics en las celdas
    fun onCellClicked(x: Int, y: Int) {
        if (!board.cells[y][x].isRevealed && !board.cells[y][x].isFlagged) {
            board.revealCell(x, y)
            val status = board.checkGameStatus()
            updateGameResult(status)
            if (status != GameStatus.InProgress) {
                endGame(status)
            }
        }
    }

    fun toggleFlag(x: Int, y: Int) {
        if (!board.cells[y][x].isRevealed) {  // Solo se puede marcar/desmarcar celdas no reveladas
            board.toggleFlag(x, y)
            val status = board.checkGameStatus()
            updateGameResult(status)
            if (status != GameStatus.InProgress) {
                endGame(status)
            }
        }
    }

    private fun updateGameResult(status: GameStatus) {
        when (status) {
            GameStatus.Won -> gameResult.value = GameResult.Won
            GameStatus.Lost -> gameResult.value = GameResult.LostByBomb
            else -> {}
        }
    }

    private fun startTimer() {
        // Iniciar el temporizador
        timerJob = viewModelScope.launch {
            while (timeRemaining.value > 0) {
                delay(1000)
                timeRemaining.value -= 1
                if (timeRemaining.value == 0) {
                    finalizeGame(GameResult.LostByTime)
                    break
                }
            }
        }
    }

    // Función para finalizar el juego y actualizar el estado basado en GameResult
    private fun finalizeGame(result: GameResult) {
        gameResult.value = result
        stopTimer()  // Detener el temporizador en cualquier finalización de juego
    }

    // Detener el temporizador y limpiar recursos
    fun stopTimer() {
        timerJob?.cancel()
    }

    // Finalizar el juego y detener el temporizador
    private fun endGame(result: GameStatus) {
        if (result == GameStatus.Lost) {
            gameResult.value = GameResult.LostByBomb
        } else if (result == GameStatus.Won) {
            gameResult.value = GameResult.Won
        }
        stopTimer()
        saveGameRecord(result)
    }

    private fun saveGameRecord(result: GameStatus) {
        viewModelScope.launch {
            val record = GameRecord(
                userName = userName.value,
                endTime = System.currentTimeMillis(),
                gridSize = gridOption.value,
                bombPercentage = bombPercentage.value,
                numBombs = calculateTotalBombs(gridOption.value, bombPercentage.value),
                timerEnabled = timerEnabled.value,
                timeTaken = 240 - timeRemaining.value,
                bombLocationX = if (result == GameStatus.Won) -1 else getBombLocation().first,
                bombLocationY = if (result == GameStatus.Won) -1 else getBombLocation().second,
                gameResult = result.name
            )
            gameDao.insert(record)
        }
    }

    private fun getBombLocation(): Pair<Int, Int> {
        for (y in board.cells.indices) {
            for (x in board.cells[y].indices) {
                if (board.cells[y][x].hasBomb && board.cells[y][x].isRevealed) {
                    return Pair(x, y)
                }
            }
        }
        return Pair(-1, -1)
    }

    // Actualizar configuraciones y reiniciar el juego
    fun updateSettings(name: String, grid: Int, timer: Boolean, percentage: Int) {
        viewModelScope.launch {
            userPreferencesRepository.updateUserName(name)
            userPreferencesRepository.updateGridSize(grid)
            userPreferencesRepository.updateTimerEnabled(timer)
            userPreferencesRepository.updateBombPercentage(percentage)

            userName.value = name
            gridOption.value = grid
            timerEnabled.value = timer
            bombPercentage.value = percentage

            resetGame()
        }
    }

    // Reiniciar el juego
    fun resetGame() {
        timeRemaining.value = 240
        stopTimer()
        initializeGame()
    }

    fun getAllGames(): Flow<List<GameRecord>> = gameDao.getAllGames()

    fun getGameById(gameId: Long): Flow<GameRecord?> = gameDao.getGameById(gameId)

    override fun onCleared() {
        super.onCleared()
        stopTimer()  // Limpiar el temporizador cuando el ViewModel se destruya
    }

    private fun calculateTotalBombs(gridSize: Int, bombPercentage: Int): Int {
        return (gridSize * gridSize * bombPercentage / 100)
    }
}
