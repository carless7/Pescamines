package com.example.pescamines.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pescamines.data.MockGameDao
import com.example.pescamines.data.MockUserPreferencesRepository
import com.example.pescamines.model.GameRecord
import com.example.pescamines.ui.theme.AppColors
import com.example.pescamines.ui.theme.PescaminesTheme
import com.example.pescamines.ui.theme.jerseyFontFamily
import com.example.pescamines.viewmodel.GameViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavController, viewModel: GameViewModel) {
    val games by viewModel.getAllGames().collectAsState(initial = emptyList())
    val isTablet = isTabletDevice()
    val selectedGameId = remember { mutableStateOf<Long?>(null) }

    Scaffold(
        topBar = { HistoryTopBar(navController) },
        containerColor = AppColors.Background
    ) { padding ->
        if (isTablet) {
            Row(modifier = Modifier
                .padding(padding)
                .fillMaxSize()) {
                GameList(
                    games = games,
                    onGameClick = { gameId ->
                        selectedGameId.value = gameId
                    },
                    modifier = Modifier.weight(1f)
                )
                GameDetails(
                    game = games.firstOrNull { it.id == selectedGameId.value } ?: games.firstOrNull(),
                    modifier = Modifier.weight(1f)
                )
            }
        } else {
            Column(modifier = Modifier
                .padding(padding)
                .fillMaxSize()) {
                GameList(
                    games = games,
                    onGameClick = { gameId ->
                        navController.navigate("gameDetails/$gameId")
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryTopBar(navController: NavController) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Historial de Partides",
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
fun GameList(games: List<GameRecord>, onGameClick: (Long) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(games) { game ->
            GameListItem(game, onGameClick)
        }
    }
}

@Composable
fun GameListItem(game: GameRecord, onGameClick: (Long) -> Unit) {
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    val formattedDate = dateFormatter.format(Date(game.endTime))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onGameClick(game.id) },
        colors = CardDefaults.cardColors(containerColor = AppColors.Background)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Jugador: ${game.userName}", color = AppColors.ColorTypography)
            Text(text = "Resultat: ${game.gameResult}", color = AppColors.ColorTypography)
            Text(text = "Data: $formattedDate", color = AppColors.ColorTypography)
        }
    }
}

@Composable
fun GameDetails(game: GameRecord?, modifier: Modifier = Modifier) {
    game?.let {
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val formattedDate = dateFormatter.format(Date(game.endTime))

        Column(modifier = modifier.padding(16.dp)) {
            Text(text = "Jugador: ${game.userName}", color = AppColors.ColorTypography)
            Text(text = "Resultat: ${game.gameResult}", color = AppColors.ColorTypography)
            Text(text = "Data: $formattedDate", color = AppColors.ColorTypography)
            Text(text = "Mida: ${game.gridSize}", color = AppColors.ColorTypography)
            Text(text = "Percentatge de Bombes: ${game.bombPercentage}%", color = AppColors.ColorTypography)
            Text(text = "Nº de Mines: ${game.numBombs}", color = AppColors.ColorTypography)
            Text(text = "Temps Emprat: ${game.timeTaken}s", color = AppColors.ColorTypography)
            Text(text = "Localització de la Bomba: (${game.bombLocationX}, ${game.bombLocationY})", color = AppColors.ColorTypography)
        }
    } ?: run {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Selecciona una partida per veure'n els detalls",
                color = AppColors.ColorTypography,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = jerseyFontFamily,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

@Composable
fun isTabletDevice(): Boolean {
    val context = LocalContext.current
    val configuration = context.resources.configuration
    val screenLayout = configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
    return (screenLayout == Configuration.SCREENLAYOUT_SIZE_LARGE
            || screenLayout == Configuration.SCREENLAYOUT_SIZE_XLARGE)
}

@Preview(showBackground = false)
@Composable
fun PreviewHistoryScreen() {
    val context = LocalContext.current
    val mockNavController = rememberNavController()
    val mockGameDao = MockGameDao()
    val mockUserPreferencesRepository = MockUserPreferencesRepository(context)
    val gameViewModel = GameViewModel(mockGameDao, mockUserPreferencesRepository)

    PescaminesTheme {
        HistoryScreen(navController = mockNavController, viewModel = gameViewModel)
    }
}
