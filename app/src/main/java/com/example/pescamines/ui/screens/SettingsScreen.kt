package com.example.pescamines.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pescamines.ui.theme.AppColors
import com.example.pescamines.ui.theme.PescaminesTheme
import com.example.pescamines.ui.theme.Pink80
import com.example.pescamines.ui.theme.jerseyFontFamily
import com.example.pescamines.viewmodel.GameViewModel

@Composable
fun SettingsScreen(navController: NavController, viewModel: GameViewModel) {
    var userName by remember { mutableStateOf(TextFieldValue(viewModel.userName.value)) }
    var gridSize by remember { mutableStateOf(viewModel.gridOption.value.toString()) }
    var bombPercentage by remember { mutableStateOf(viewModel.bombPercentage.value.toString()) }
    var timerEnabled by remember { mutableStateOf(viewModel.timerEnabled.value) }

    Column(modifier = Modifier.fillMaxSize().padding(top = 20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center ) {
        Text(
            text = "Configuració del joc",
            fontSize = 36.sp,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(5f,5f),
                    blurRadius = 10f
                )
            ),
            fontFamily = jerseyFontFamily,
            color = AppColors.ColorTypography,
            textAlign = TextAlign.Center
            )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            label = {Text(text = "Nom de l\'usuari",
                fontSize = 20.sp,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(5f,5f),
                        blurRadius = 10f
                    )
                ),
                fontFamily = jerseyFontFamily,
                color = AppColors.ColorTypography,
                textAlign = TextAlign.Center) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = AppColors.ColorTypography,
                focusedBorderColor = AppColors.ColorTypography,
                unfocusedLabelColor = AppColors.ColorTypography,
                focusedLabelColor = AppColors.ColorTypography,
                unfocusedTextColor = AppColors.ColorTypography,
                focusedTextColor = AppColors.ColorTypography
            ),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = gridSize,
            onValueChange = {
                val input = it.toIntOrNull()
                if (input != null && input > 5 && input < 30) {
                    gridSize = it
                }
            },
            label = { Text(
                text = "Mida",
                fontSize = 20.sp,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(5f,5f),
                        blurRadius = 10f
                    )
                ),
                fontFamily = jerseyFontFamily,
                color = AppColors.ColorTypography,
                textAlign = TextAlign.Center
                ) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = AppColors.ColorTypography,
                focusedBorderColor = AppColors.ColorTypography,
                unfocusedLabelColor = AppColors.ColorTypography,
                focusedLabelColor = AppColors.ColorTypography,
                unfocusedTextColor = AppColors.ColorTypography,
                focusedTextColor = AppColors.ColorTypography
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = bombPercentage,
            onValueChange = {
                val input = it.toIntOrNull()
                if (input != null && input > 1 && input < 99) {
                    bombPercentage = it
                }
            },
            label = { Text(text = "Percentatge de Bombes",
                fontSize = 20.sp,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(5f,5f),
                        blurRadius = 10f
                    )
                ),
                fontFamily = jerseyFontFamily,
                color = AppColors.ColorTypography,
                textAlign = TextAlign.Center
            ) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = AppColors.ColorTypography,
                focusedBorderColor = AppColors.ColorTypography,
                unfocusedLabelColor = AppColors.ColorTypography,
                focusedLabelColor = AppColors.ColorTypography,
                unfocusedTextColor = AppColors.ColorTypography,
                focusedTextColor = AppColors.ColorTypography
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Temporizador ",
                fontSize = 20.sp,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(5f,5f),
                        blurRadius = 10f
                    )
                ),
                fontFamily = jerseyFontFamily,
                color = AppColors.ColorTypography,
                textAlign = TextAlign.Center
            )
            Switch(
                checked = timerEnabled,
                onCheckedChange = { timerEnabled = it },
                colors  = SwitchDefaults.colors(
                    checkedThumbColor = Color.Blue,
                    checkedTrackColor = AppColors.MainButton,
                    uncheckedThumbColor = AppColors.SecondaryButton,
                    uncheckedTrackColor= Pink80,
                ),
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
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.SecondaryButton,
                contentColor = Color.White
            )
        ) {

            Icon(Icons.Filled.PlayArrow, tint = Color.White ,contentDescription = "Configurar el joc")
            Text(
                text = "Començar la partida",
                fontSize = 20.sp,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(5f,5f),
                        blurRadius = 10f
                    )
                ),
                fontFamily = jerseyFontFamily,
                color = AppColors.ColorTypography,
                textAlign = TextAlign.Center
                )
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



