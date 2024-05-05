package com.example.pescamines.navigation

import GameViewModel
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.pescamines.ui.screens.HomeScreen
import com.example.pescamines.ui.screens.HelpScreen
//import com.example.pescamines.ui.screens.SettingsScreen
//import com.example.pescamines.ui.screens.GameScreen
//import com.example.pescamines.ui.screens.ResultsScreen
//import com.example.pescamines.viewmodel.GameViewModel


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("help") { HelpScreen(navController) }
        //composable("settings") { SettingsScreen(navController) }
        //composable("game") { GameScreen(navController) }
        //composable("results") { ResultsScreen(navController) }
    }
}
