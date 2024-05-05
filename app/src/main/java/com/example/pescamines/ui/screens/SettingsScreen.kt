package com.example.pescamines.ui.screens
/*
import GameViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pescamines.viewmodel.GameViewModel

@Composable
fun SettingsScreen(navController: NavController, viewModel: GameViewModel) {
    var width by remember { mutableStateOf(viewModel.width.value.toString()) }
    var numBombs by remember { mutableStateOf(viewModel.numBombs.value.toString()) }
    var timerEnabled by remember { viewModel.timerEnabled.collectAsState() }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = width,
            onValueChange = { width = it },
            label = { Text("Mida de la graella") }
        )
        TextField(
            value = numBombs,
            onValueChange = { numBombs = it },
            label = { Text("Porcentaje de bombes") }
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = timerEnabled,
                onCheckedChange = { timerEnabled = it }
            )
            Text("Activar temporitzador")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.updateSettings(width.toInt(), numBombs.toInt(), timerEnabled)
            navController.navigate("game")
        }) {
            Text("Guardar configuraciones y empezar partida")
        }
    }
}*/