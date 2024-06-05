package com.example.pescamines.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pescamines.ui.theme.AppColors
import com.example.pescamines.ui.theme.PescaminesTheme
import com.example.pescamines.ui.theme.jerseyFontFamily

@Composable
fun HelpScreen(navController: NavController) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    val titleFontSize = when {
        screenWidth < 360 -> 28.sp
        screenWidth < 480 -> 32.sp
        screenWidth < 720 -> 36.sp
        else -> 48.sp
    }

    val textFontSize = when {
        screenWidth < 360 -> 18.sp
        screenWidth < 480 -> 20.sp
        screenWidth < 720 -> 22.sp
        else -> 24.sp
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    tint = AppColors.SecondaryButton,
                    contentDescription = "Back",
                    modifier = Modifier.size(48.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Com jugar",
                fontSize = titleFontSize,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(5f, 5f),
                        blurRadius = 10f
                    )
                ),
                fontFamily = jerseyFontFamily,
                fontWeight = FontWeight.Bold,
                color = AppColors.ColorTypography,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Et sents com un nen croata als anys 90? No passa res!",
                    fontSize = textFontSize,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(3f, 3f),
                            blurRadius = 5f
                        )
                    ),
                    fontFamily = jerseyFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.ColorTypography,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Aquí tens una petita guia de què tracta i com jugar al pescamines:",
                    fontSize = textFontSize,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(3f, 3f),
                            blurRadius = 5f
                        )
                    ),
                    fontFamily = jerseyFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = AppColors.ColorTypography,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "El joc tracta de revel·lar totes les caselles d'una graella que no amaguin una mina. Algunes caselles tenen un número, aquest número indica les mines que sumen totes les caselles circumdants. Així, si una casella té el número 3, vol dir que de les vuit caselles que hi ha al voltant (si no és en una cantonada o vora) n'hi ha 3 amb mines i 5 sense mines.",
                    fontSize = textFontSize,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(3f, 3f),
                            blurRadius = 5f
                        )
                    ),
                    fontFamily = jerseyFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = AppColors.ColorTypography,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Si prems durnat una estona en una casella la pots marcar amb una bandera.",
                    fontSize = textFontSize,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(3f, 3f),
                            blurRadius = 5f
                        )
                    ),
                    fontFamily = jerseyFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = AppColors.ColorTypography,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Si es descobreix una casella amb una mina es perd la partida !!",
                    fontSize = textFontSize,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(3f, 3f),
                            blurRadius = 5f
                        )
                    ),
                    fontFamily = jerseyFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = AppColors.ColorTypography,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Descobrir totes les caselles sense mina significa guanyar la partida !!",
                    fontSize = textFontSize,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(3f, 3f),
                            blurRadius = 5f
                        )
                    ),
                    fontFamily = jerseyFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = AppColors.ColorTypography,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "Bona sort camarada! :)",
                    fontSize = textFontSize,
                    style = TextStyle(
                        shadow = Shadow(
                            color = AppColors.SecondaryButton,
                            offset = Offset(3f, 3f),
                            blurRadius = 5f
                        )
                    ),
                    fontFamily = jerseyFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.ColorTypography,
                    textAlign = TextAlign.Center
                )
            }
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
