package com.example.pescamines.navigation

import com.example.pescamines.viewmodel.GameViewModel
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.pescamines.ui.screens.HomeScreen
import com.example.pescamines.ui.screens.HelpScreen
import com.example.pescamines.ui.screens.SettingsScreen

//import com.example.pescamines.viewmodel.com.example.pescamines.viewmodel.GameViewModel


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val gameViewModel: GameViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("help") {
            HelpScreen(navController)
        }
        composable("settings") {
            SettingsScreen(navController, gameViewModel)
        }
        /*
        composable("game") {
            GameScreen(navController, gameViewModel)
        }
        composable("results") {
            ResultsScreen(navController, gameViewModel)
        }*/
    }
}
