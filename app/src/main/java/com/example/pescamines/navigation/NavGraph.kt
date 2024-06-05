package com.example.pescamines.navigation

import GameScreen
import com.example.pescamines.viewmodel.GameViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pescamines.ui.screens.GameDetailsScreen
import com.example.pescamines.ui.screens.HomeScreen
import com.example.pescamines.ui.screens.HelpScreen
import com.example.pescamines.ui.screens.HistoryScreen
import com.example.pescamines.ui.screens.ResultsScreen
import com.example.pescamines.ui.screens.SettingsScreen

@Composable
fun AppNavigation(gameViewModel: GameViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController, gameViewModel)
        }
        composable("help") {
            HelpScreen(navController)
        }
        composable("settings") {
            SettingsScreen(navController, gameViewModel)
        }
        composable("game") {
            GameScreen(navController, gameViewModel)
        }
        composable("results") {
            ResultsScreen(navController, gameViewModel)
        }
        composable("history") {
            HistoryScreen(navController, gameViewModel)
        }
        composable(
            "gameDetails/{gameId}",
            arguments = listOf(navArgument("gameId") { defaultValue = -1L })
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getLong("gameId") ?: return@composable
            GameDetailsScreen(navController = navController, gameId = gameId, viewModel = gameViewModel)
        }
    }
}

