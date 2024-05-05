package com.example.pescamines.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.example.pescamines.ui.theme.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.pescamines.ui.theme.PescaminesTheme
import kotlin.system.exitProcess

@Composable
fun HomeScreen(navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(
                onClick = { exitProcess(0) }
            ) {
                Icon(
                    Icons.Filled.ExitToApp,
                    tint = AppColors.SecondaryButton,
                    contentDescription = "Sortir",
                    modifier = Modifier.size(128.dp)
                )

            }
            IconButton(
                onClick = { navController.navigate("help") }
            ) {
                Icon(
                    Icons.Filled.Info,
                    tint = AppColors.SecondaryButton,
                    contentDescription = "Ajuda",
                    modifier = Modifier.size(128.dp)
                    )
            }
        }
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp), horizontalArrangement = Arrangement.Center) {
            Text(
                text = "PescaMines",
                fontSize = 42.sp,
                style = TextStyle(
                    shadow = Shadow(
                        color = AppColors.SecondaryButton,
                        offset = Offset(5f,5f),
                        blurRadius = 10f
                    )
                ),
                fontFamily = jerseyFontFamily,
                color = AppColors.ColorTypography
            )
        }
        Spacer(modifier = Modifier.height(30.dp))

        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = { navController.navigate("settings") },
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.SecondaryButton, // Color de fondo del botón
                contentColor = Color.White // Color del texto y del icono
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Icon(Icons.Filled.PlayArrow, tint = Color.White ,contentDescription = "Configurar el joc")
            Text("Començar una partida")
        }
        Button(onClick = { navController.navigate("results") }) {

        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    PescaminesTheme {
        val mockNavController = rememberNavController()
        HomeScreen(navController = mockNavController)
    }
}