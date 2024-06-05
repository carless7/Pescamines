package com.example.pescamines.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pescamines.data.MockGameDao
import com.example.pescamines.data.MockUserPreferencesRepository
import com.example.pescamines.ui.theme.PescaminesTheme
import com.example.pescamines.viewmodel.GameViewModel

@Composable
fun GameDetailsScreen(navController: NavController, gameId: Long, viewModel: GameViewModel) {
    val game by viewModel.getGameById(gameId).collectAsState(initial = null)
    GameDetails(game = game)
}

@Preview(showBackground = false)
@Composable
fun PreviewGameDetailsScreen() {
    val context = LocalContext.current
    val mockNavController = rememberNavController()
    val mockGameDao = MockGameDao()
    val mockUserPreferencesRepository = MockUserPreferencesRepository(context)
    val gameViewModel = GameViewModel(mockGameDao, mockUserPreferencesRepository)

    PescaminesTheme {
        GameDetailsScreen(navController = mockNavController, 1 ,viewModel = gameViewModel)
    }
}

