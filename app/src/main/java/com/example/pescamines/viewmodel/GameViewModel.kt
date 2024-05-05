package com.example.pescamines.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.example.pescamines.model.Board
import com.example.pescamines.model.BombManager
import com.example.pescamines.model.GameStatus
import com.example.pescamines.model.NumberCalculator
import com.example.pescamines.viewmodel.GameResult

class GameViewModel : ViewModel() {
    // Configuración del juego
    val userName = MutableStateFlow("")
    val gridOption = MutableStateFlow(6)  // Tamaño del grid por defecto
    val timerEnabled = MutableStateFlow(false)
    val bombPercentage = MutableStateFlow(15)  // Porcentaje de bombas por defecto
    val timeRemaining = MutableStateFlow(120)  // Temporizador en segundos
    val gameResult = MutableStateFlow(GameResult.InProgress)

    // Referencias al modelo de datos del juego
    lateinit var board: Board
    private lateinit var bombManager: BombManager
    private lateinit var numberCalculator: NumberCalculator

    private var timerJob: Job? = null  // Trabajo del temporizador

    init {
        initializeGame()
    }

    // Inicializar el juego con la configuración actual
    private fun initializeGame() {
        bombManager = BombManager((gridOption.value * gridOption.value * bombPercentage.value / 100).toInt())
        numberCalculator = NumberCalculator()
        board = Board(gridOption.value)
        board.initialize(bombManager, numberCalculator)
        if (timerEnabled.value) {
            startTimer()
        }
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
            val status = board.checkGameStatus()  // Si tu juego evalúa ganar con banderas correctas, de lo contrario, omite esto
            updateGameResult(status)
            if (status != GameStatus.InProgress) {
                endGame(status)
            }
        }
    }
    private fun updateGameResult(status: GameStatus) {
        when(status) {
            GameStatus.Won -> gameResult.value = GameResult.Won
            GameStatus.Lost -> gameResult.value = GameResult.LostByBomb
            else -> {} // No actualización necesaria
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
    private fun stopTimer() {
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
    }
    // Actualizar configuraciones y reiniciar el juego
    fun updateSettings(name: String, grid: Int, timer: Boolean, percentage: Int) {
        if (grid < 5 || grid > 30) throw IllegalArgumentException("Grid size must be between 5 and 30")
        if (percentage <= 0 || percentage >= 100) throw IllegalArgumentException("Bomb percentage must be between 1 and 99")

        userName.value = name
        gridOption.value = grid
        timerEnabled.value = timer
        bombPercentage.value = percentage
        resetGame()
    }

    // Reiniciar el juego
    fun resetGame() {
        timeRemaining.value = 120
        stopTimer()
        initializeGame()
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()  // Limpiar el temporizador cuando el ViewModel se destruya
    }

}
