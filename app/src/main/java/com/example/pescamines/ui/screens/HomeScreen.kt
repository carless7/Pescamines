package com.example.pescamines.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import com.example.pescamines.ui.theme.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.pescamines.ui.theme.PescaminesTheme
import com.example.pescamines.viewmodel.GameViewModel
import kotlin.system.exitProcess

@Composable
fun HomeScreen(navController: NavController, viewModel: GameViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { exitProcess(0) }
            ) {
                Icon(
                    Icons.Filled.ExitToApp,
                    tint = AppColors.SecondaryButton,
                    contentDescription = "Sortir",
                    modifier = Modifier.size(64.dp)
                )
            }
            IconButton(
                onClick = { navController.navigate("help") }
            ) {
                Icon(
                    Icons.Filled.Info,
                    tint = AppColors.SecondaryButton,
                    contentDescription = "Ajuda",
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val fontSize = when {
        screenWidth < 360 -> 48.sp
        screenWidth < 480 -> 64.sp
        screenWidth < 720 -> 72.sp
        else -> 90.sp
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "PescaMines",
                fontSize = fontSize,
                style = TextStyle(
                    shadow = Shadow(
                        color = AppColors.SecondaryButton,
                        offset = Offset(5f, 5f),
                        blurRadius = 10f
                    )
                ),
                fontFamily = jerseyFontFamily,
                color = AppColors.ColorTypography
            )
        }
        Spacer(modifier = Modifier.height(100.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { navController.navigate("history") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.SecondaryButton,
                    contentColor = Color.White
                ),
                modifier = Modifier.size(80.dp)
            ) {
                Icon(
                    Icons.Filled.List,
                    contentDescription = "Llistat de partides",
                    modifier = Modifier.size(48.dp)
                )
            }
            Button(
                onClick = {
                    viewModel.initializeGame()
                    navController.navigate("game")
                          },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.SecondaryButton,
                    contentColor = Color.White
                ),
                modifier = Modifier.size(100.dp)
            ) {
                Icon(
                    Icons.Filled.PlayArrow,
                    contentDescription = "Començar la partida",
                    modifier = Modifier.size(60.dp)
                )
            }
            Button(
                onClick = { navController.navigate("settings") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.SecondaryButton,
                    contentColor = Color.White
                ),
                modifier = Modifier.size(80.dp)
            ) {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = "Configuració",
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    PescaminesTheme {
        val mockNavController = rememberNavController()
        val gameViewModel = GameViewModel()
        HomeScreen(navController = mockNavController, viewModel = gameViewModel)
    }
}
