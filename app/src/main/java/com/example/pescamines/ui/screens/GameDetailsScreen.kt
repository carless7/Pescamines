package com.example.pescamines.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pescamines.data.MockGameDao
import com.example.pescamines.data.MockUserPreferencesRepository
import com.example.pescamines.data.GameRecord
import com.example.pescamines.ui.theme.AppColors
import com.example.pescamines.ui.theme.PescaminesTheme
import com.example.pescamines.ui.theme.jerseyFontFamily
import com.example.pescamines.viewmodel.GameViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailsScreen(navController: NavController, gameId: Long, viewModel: GameViewModel) {
    val game by viewModel.getGameById(gameId).collectAsState(initial = null)

    Scaffold(
        topBar = { GameDetailsTopBar(navController) },
        containerColor = AppColors.Background
    ) { padding ->
        GameDetail(game = game, modifier = Modifier.padding(padding).fillMaxSize())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailsTopBar(navController: NavController) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Detalls de la Partida",
                fontSize = 32.sp,
                style = TextStyle(
                    shadow = Shadow(
                        color = AppColors.SecondaryButton,
                        offset = Offset(3f, 3f),
                        blurRadius = 5f
                    )
                ),
                fontFamily = jerseyFontFamily,
                color = AppColors.ColorTypography,
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    tint = AppColors.SecondaryButton,
                    contentDescription = "Back",
                    modifier = Modifier.size(64.dp)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = AppColors.Background,
            titleContentColor = AppColors.ColorTypography,
            actionIconContentColor = AppColors.SecondaryButton
        )
    )
}

@Composable
fun GameDetail(game: GameRecord?, modifier: Modifier = Modifier) {
    game?.let {
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val formattedDate = dateFormatter.format(Date(game.endTime))

        Column(
            modifier = modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Jugador: ${game.userName}",
                style = TextStyle(
                    color = AppColors.ColorTypography,
                    fontSize = 24.sp,
                    fontFamily = jerseyFontFamily
                )
            )
            Text(
                text = "Resultat: ${game.gameResult}",
                style = TextStyle(
                    color = AppColors.ColorTypography,
                    fontSize = 24.sp,
                    fontFamily = jerseyFontFamily
                )
            )
            Text(
                text = "Data: $formattedDate",
                style = TextStyle(
                    color = AppColors.ColorTypography,
                    fontSize = 24.sp,
                    fontFamily = jerseyFontFamily
                )
            )
            Text(
                text = "Mida: ${game.gridSize}",
                style = TextStyle(
                    color = AppColors.ColorTypography,
                    fontSize = 24.sp,
                    fontFamily = jerseyFontFamily
                )
            )
            Text(
                text = "Percentatge de Bombes: ${game.bombPercentage}%",
                style = TextStyle(
                    color = AppColors.ColorTypography,
                    fontSize = 24.sp,
                    fontFamily = jerseyFontFamily
                )
            )
            Text(
                text = "Nº de Mines: ${game.numBombs}",
                style = TextStyle(
                    color = AppColors.ColorTypography,
                    fontSize = 24.sp,
                    fontFamily = jerseyFontFamily
                )
            )
            Text(
                text = "Temps Emprat: ${game.timeTaken}s",
                style = TextStyle(
                    color = AppColors.ColorTypography,
                    fontSize = 24.sp,
                    fontFamily = jerseyFontFamily
                )
            )
            Text(
                text = "Localització de la Bomba: (${game.bombLocationX}, ${game.bombLocationY})",
                style = TextStyle(
                    color = AppColors.ColorTypography,
                    fontSize = 24.sp,
                    fontFamily = jerseyFontFamily
                )
            )
        }
    } ?: run {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Selecciona una partida per veure'n els detalls",
                color = AppColors.ColorTypography,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = jerseyFontFamily
                )
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewGameDetailsScreen() {
    val context = LocalContext.current
    val mockNavController = rememberNavController()
    val mockGameDao = MockGameDao()
    val mockUserPreferencesRepository = MockUserPreferencesRepository(context)
    val gameViewModel = GameViewModel(mockGameDao, mockUserPreferencesRepository)

    PescaminesTheme {
        GameDetailsScreen(navController = mockNavController, gameId = 1, viewModel = gameViewModel)
    }
}
