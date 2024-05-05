package com.example.pescamines.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.pescamines.ui.theme.AppColors
import com.example.pescamines.ui.theme.PescaminesTheme
import kotlin.system.exitProcess

@Composable
fun HelpScreen(navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 20.dp), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top ) {
        Row {
            IconButton(onClick = { navController.popBackStack()} ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    tint = AppColors.SecondaryButton,
                    contentDescription = "Back",
                    modifier = Modifier.size(128.dp)
                )
            }
            Spacer(modifier = Modifier.padding(25.dp))
            Text(
                text = "Com jugar",
                fontSize = 42.sp,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(5f,5f),
                        blurRadius = 10f
                    )
                )
            )
        }
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 80.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center ) {
        Text(
            text = "Et sents com un nen croata als anys 90? No passa res! Aquí tens una petita guia de què tracta i com jugar al pescamines:\n" +
                    "\n" +
                    "El joc tracta de revel·lar totes les caselles d\\'una graella que no amaguin una mina. Algunes caselles tenen un número, aquest número indica les mines que sumen totes les caselles circumdants. Així, si una casella té el número 3, vol dir que de les vuit caselles que hi ha al voltant (si no és en una cantonada o vora) n\\'hi ha 3 amb mines i 5 sense mines.\n" +
                    "\n" +
                    "Si es descobreix una casella amb una mina es perd la partida !!\n" +
                    "\n" +
                    "Descobrir totes les caselles sense mina significa guanyar la partida !!\n" +
                    "\n" +
                    "Bona sort camarada! :)",
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        //TODO: put logo of app or image example
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
