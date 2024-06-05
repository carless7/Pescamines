package com.example.pescamines.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pescamines.data.Game
import com.example.pescamines.data.GamesRepository

class GameEntryViewModel(private val gameRepository: GamesRepository) : ViewModel()  {
    var gameUiState by mutableStateOf(GameUiState())
        private set
    fun updateUiState(gameDetails: GameDetails) {
        gameUiState =
            GameUiState(gameDetails = gameDetails, isEntryValid = validateInput(gameDetails))
    }

    suspend fun saveGame() {
        if (validateInput()) {
            gameRepository.insertGame(gameUiState.gameDetails.toGame())
        }
    }

    private fun validateInput(uiState: GameDetails = gameUiState.gameDetails): Boolean {
        return with(uiState) {
            player.isNotBlank()
        }
    }
}
data class GameUiState(
    val gameDetails: GameDetails = GameDetails(),
    val isEntryValid: Boolean = false
)

data class GameDetails(
    val id: Int = 0,
    val player: String = "Aitana Bonmatí",
    val temporitzador: Boolean = true,
    val temps_restant: Int = 240,
    val grid_size: Int = 8,
    val percentage: Int = 10,
)

fun GameDetails.toGame(): Game = Game(
    id = id,
    player = player,
    temporitzador = temporitzador,
    temps_restant = temps_restant,
    grid_size = grid_size,
    percentage = percentage,
)

fun Game.toItemUiState(isEntryValid: Boolean = false): GameUiState = GameUiState(
    gameDetails = this.toGameDetails(),
    isEntryValid = isEntryValid
)

fun Game.toGameDetails(): GameDetails = GameDetails(
    id = id,
    player = player,
    temporitzador = temporitzador,
    temps_restant = temps_restant,
    grid_size = grid_size,
    percentage = percentage,
)