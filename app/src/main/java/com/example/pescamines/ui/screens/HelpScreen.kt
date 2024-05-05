package com.example.pescamines.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.pescamines.ui.theme.PescaminesTheme
import kotlin.system.exitProcess

@Composable
fun HelpScreen(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Aquí va el texto de cómo jugar al juego...")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Volver")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewHelpScreen() {
    PescaminesTheme {
        val mockNavController = rememberNavController()
        HelpScreen(navController = mockNavController)
    }
}
