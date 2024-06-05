package com.example.pescamines.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pescamines.data.MockGameDao
import com.example.pescamines.data.MockUserPreferencesRepository
import com.example.pescamines.model.GameRecord
import com.example.pescamines.ui.theme.AppColors
import com.example.pescamines.ui.theme.PescaminesTheme
import com.example.pescamines.viewmodel.GameViewModel

@Composable
fun HistoryScreen(navController: NavController, viewModel: GameViewModel) {
    val games by viewModel.getAllGames().collectAsState(initial = emptyList())
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600

    if (isTablet) {
        Row(modifier = Modifier.fillMaxSize()) {
            GameList(
                games = games,
                onGameClick = { gameId ->
                    navController.navigate("gameDetails/$gameId")
                },
                modifier = Modifier.weight(1f)
            )
            GameDetails(
                game = games.firstOrNull(),
                modifier = Modifier.weight(2f)
            )
        }
    } else {
        GameList(
            games = games,
            onGameClick = { gameId ->
                navController.navigate("gameDetails/$gameId")
            }
        )
    }
}

@Composable
fun GameList(games: List<GameRecord>, onGameClick: (Long) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(games) { game ->
            GameListItem(game, onGameClick)
        }
    }
}

@Composable
fun GameListItem(game: GameRecord, onGameClick: (Long) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onGameClick(game.id) },
        colors = CardDefaults.cardColors(containerColor = AppColors.Background)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Jugador: ${game.userName}")
            Text(text = "Resultat: ${game.gameResult}")
            Text(text = "Data: ${game.endTime}")
        }
    }
}

@Composable
fun GameDetails(game: GameRecord?, modifier: Modifier = Modifier) {
    game?.let {
        Column(modifier = modifier.padding(16.dp)) {
            Text(text = "Jugador: ${game.userName}")
            Text(text = "Resultat: ${game.gameResult}")
            Text(text = "Data: ${game.endTime}")
            Text(text = "Mida: ${game.gridSize}")
            Text(text = "Percentatge de Bombes: ${game.bombPercentage}%")
            Text(text = "Temps Emprat: ${game.timeTaken}s")
            Text(text = "Localització de la Bomba: (${game.bombLocationX}, ${game.bombLocationY})")
        }
    } ?: run {
        Text(text = "Selecciona una partida per veure'n els detalls", modifier = modifier.padding(16.dp))
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewHistoryScreen() {
    val context = LocalContext.current
    val mockNavController = rememberNavController()
    val mockGameDao = MockGameDao()
    val mockUserPreferencesRepository = MockUserPreferencesRepository(context)
    val gameViewModel = GameViewModel(mockGameDao, mockUserPreferencesRepository)

    PescaminesTheme {
        HistoryScreen(navController = mockNavController, viewModel = gameViewModel)
    }
}
