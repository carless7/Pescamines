package com.example.pescamines.ui.screens

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.example.pescamines.viewmodel.GameViewModel
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pescamines.ui.theme.AppColors
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun ResultsScreen(navController: NavHostController, viewModel: GameViewModel) {
    val activity = (LocalContext.current as? Activity)
    val context = LocalContext.current
    var email by remember { mutableStateOf(TextFieldValue(""))}
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Resultat de la partida: ${viewModel.gameResult}")
        Spacer(modifier = Modifier.height(8.dp))
        val sdf = SimpleDateFormat("'Data de la partida\n'dd-MM-yyyy '\n\nHora de la partida\n\nTime\n'HH:mm:ss z")
        val maildateformat = SimpleDateFormat("dd-MM-yyyy / HH:mm:ss z")
        val currentDateAndTime = sdf.format(Date())
        val maildate = maildateformat.format(Date())
        Text(
            text = currentDateAndTime,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
        Row {
            OutlinedTextField(
                value = email,
                onValueChange = {newEmail ->
                    email = newEmail
                }

            )
            IconButton(onClick = {
                Toast.makeText(context, "Correu a on enviar el resultat", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    Icons.Filled.Info,
                    tint = AppColors.SecondaryButton,
                    contentDescription = "Mail_hint",
                    modifier = Modifier.size(128.dp)
                )
            }
        }
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(text = "Sortir")
                Spacer(modifier = Modifier.padding(8.dp))
                IconButton(onClick = { activity?.finish() }) {
                    Icon(
                        Icons.Filled.ExitToApp,
                        tint = AppColors.SecondaryButton,
                        contentDescription = "Exit",
                        modifier = Modifier.size(128.dp)
                    )
                }
            }
            Column {
                Text(text = "Tornar a jugar")
                Spacer(modifier = Modifier.padding(8.dp))
                IconButton(onClick = { navController.popBackStack("settings", inclusive = false) }) {
                    Icon(
                        Icons.Filled.ExitToApp,
                        tint = AppColors.SecondaryButton,
                        contentDescription = "play_again",
                        modifier = Modifier.size(128.dp)
                    )
                }

            }
            Column {
                Text(text = "Enviar mail")
                Spacer(modifier = Modifier.padding(8.dp))
                IconButton(onClick = {
                    val i = Intent(Intent.ACTION_SEND)
                    val emailAddr = email.text
                    val emailSubj = "Partida $maildate"
                    val emailBody = "Resultat ${viewModel.gameResult}"
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

