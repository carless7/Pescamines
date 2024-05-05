package com.example.pescamines.ui.screens

import com.example.pescamines.viewmodel.GameViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pescamines.ui.theme.PescaminesTheme

@Composable
fun SettingsScreen(navController: NavController, viewModel: GameViewModel) {
    var userName by remember { mutableStateOf(TextFieldValue(viewModel.userName.value)) }
    var gridSize by remember { mutableStateOf(viewModel.gridOption.value.toString()) }
    var bombPercentage by remember { mutableStateOf(viewModel.bombPercentage.value.toString()) }
    var timerEnabled by remember { mutableStateOf(viewModel.timerEnabled.value) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Configuració del joc")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("Nom del jugador") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = gridSize,
            onValueChange = { gridSize = it },
            label = { Text("Mida") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = bombPercentage,
            onValueChange = { bombPercentage = it },
            label = { Text("Percentatge de Bombes") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Temporizador ")
            Switch(
                checked = timerEnabled,
                onCheckedChange = { timerEnabled = it }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.updateSettings(
                    userName.text,
                    gridSize.toInt(),
                    timerEnabled,
                    bombPercentage.toInt()
                )
                navController.navigate("game")
            }
        ) {
            Icon(Icons.Filled.PlayArrow, tint = Color.White ,contentDescription = "Configurar el joc")
            Text("Començar la partida")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    PescaminesTheme {
        val mockNavController = rememberNavController()
        val gameViewModel = GameViewModel()
        SettingsScreen(navController = mockNavController, viewModel = gameViewModel)
    }
}



