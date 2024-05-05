package com.example.pescamines.ui.screens

import com.example.pescamines.viewmodel.GameViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultsScreen(viewModel: GameViewModel, navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Resultado de la partida: ${viewModel.gameResult}")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate("settings") }) {
            Text("Volver a empezar")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Salir de la App")
        }
    }
}
