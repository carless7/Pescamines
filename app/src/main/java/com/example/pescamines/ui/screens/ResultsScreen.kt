package com.example.pescamines.ui.screens

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pescamines.ui.theme.AppColors
import com.example.pescamines.ui.theme.PescaminesTheme
import com.example.pescamines.ui.theme.jerseyFontFamily
import com.example.pescamines.viewmodel.GameViewModel
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun ResultsScreen(navController: NavHostController, viewModel: GameViewModel) {
    val activity = (LocalContext.current as? Activity)
    val context = LocalContext.current
    val userName by viewModel.userName.collectAsState()
    val gameResult by viewModel.gameResult.collectAsState()

    val emailSend = remember {
        mutableStateOf(TextFieldValue())
    }

    Column(modifier = Modifier.padding(50.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text ="Resultat de la partida: $gameResult",
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
        Spacer(modifier = Modifier.height(16.dp))
        val sdf = SimpleDateFormat("'Data de la partida:\n'dd-MM-yyyy '\nHora de la partida:\n'HH:mm:ss z")
        val maildateformat = SimpleDateFormat("dd-MM-yyyy / HH:mm:ss z")
        val currentDateAndTime = sdf.format(Date())
        val maildate = maildateformat.format(Date())
        Text(
            text = currentDateAndTime,
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
        Spacer(modifier = Modifier.height(40.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedTextField(
                value = emailSend.value,
                onValueChange = {emailSend.value = it},
                label = { Text(text = "Correu electrònic",
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
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)

            )
            IconButton(onClick = {
                Toast.makeText(context, "Correu a on enviar el resultat", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    Icons.Filled.Info,
                    tint = AppColors.SecondaryButton,
                    contentDescription = "Mail_hint",
                    modifier = Modifier
                        .size(40.dp)
                )
            }
        }
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom ) {
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Column (horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { activity?.finish() }) {
                        Icon(
                            Icons.Filled.ExitToApp,
                            tint = AppColors.SecondaryButton,
                            contentDescription = "Exit",
                            modifier = Modifier.size(128.dp)
                        )
                    }
                }
                Column (horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { navController.popBackStack("settings", inclusive = false) }) {
                        Icon(
                            Icons.Filled.Refresh,
                            tint = AppColors.SecondaryButton,
                            contentDescription = "play_again",
                            modifier = Modifier.size(128.dp),

                        )
                    }

                }
                Column (horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = {
                        val i = Intent(Intent.ACTION_SEND)
                        val emailAddr = arrayOf(emailSend.value.text)
                        val emailSubj = "Partida $maildate"
                        val emailBody = "Jugador: ${userName} \n Resultat: ${gameResult}"

                        i.putExtra(Intent.EXTRA_EMAIL, emailAddr)
                        i.putExtra(Intent.EXTRA_SUBJECT, emailSubj)
                        i.putExtra(Intent.EXTRA_TEXT, emailBody)

                        i.setType("message/rfc822")
                        context.startActivity(i)
                    }) {
                        Icon(
                            Icons.Filled.Email,
                            tint = AppColors.SecondaryButton,
                            contentDescription = "send",
                            modifier = Modifier.size(128.dp)
                        )
                    }
                }
            }
        }
    }
}
@Preview(showBackground = false)
@Composable
fun PreviewResultsScreen() {
    PescaminesTheme {
        val mockNavController = rememberNavController()
        val gameViewModel = GameViewModel()
        ResultsScreen(navController = mockNavController, viewModel = gameViewModel)
    }
}


