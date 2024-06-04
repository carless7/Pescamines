import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pescamines.model.Cell
import com.example.pescamines.ui.theme.AppColors
import com.example.pescamines.ui.theme.PescaminesTheme
import com.example.pescamines.ui.theme.jerseyFontFamily
import com.example.pescamines.viewmodel.GameResult
import com.example.pescamines.viewmodel.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(navController: NavController, viewModel: GameViewModel) {
    val timeRemaining by viewModel.timeRemaining.collectAsState()
    val bombPercentage by viewModel.bombPercentage.collectAsState()
    val gridValue by viewModel.gridOption.collectAsState()
    val timeEnabled by viewModel.timerEnabled.collectAsState()
    val totalBombs = calculateTotalBombs(gridValue, bombPercentage)
    val gameResult by viewModel.gameResult.collectAsState()

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val fontSize = when {
        screenWidth < 360 -> 40.sp
        screenWidth < 480 -> 50.sp
        screenWidth < 720 -> 60.sp
        else -> 28.sp
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "PescaMines",
                        fontSize = fontSize,
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
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()} ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            tint = AppColors.SecondaryButton,
                            contentDescription = "Back",
                            modifier = Modifier.size(128.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.resetGame() }) {
                        Icon(
                            Icons.Filled.Refresh,
                            tint = AppColors.SecondaryButton,
                            contentDescription = "Reiniciar partida",
                            modifier = Modifier.size(72.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AppColors.Background,  // Define el color de fondo del AppBar
                    titleContentColor = AppColors.ColorTypography,  // Define el color del texto del título
                    actionIconContentColor = AppColors.SecondaryButton  // Define el color de los iconos de las acciones
                )
            )

        },
        containerColor = AppColors.Background
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .fillMaxSize()) {
            Spacer(modifier = Modifier.height(60.dp))
            GameBoard(viewModel)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(timeEnabled){
                    Text("⌛: $timeRemaining s", color = AppColors.ColorTypography)
                }
                Text("\uD83D\uDCA3: $totalBombs ($bombPercentage%)", color = AppColors.ColorTypography)
            }
        }
    }
    when(gameResult != GameResult.InProgress){
        true -> {
            navController.navigate("results")
        }
        false -> {}
    }
    // Mostrar un diálogo cuando el juego finaliza
    /*while(gameResult != GameResult.InProgress){
        ShowResultDialog(gameResult, navController)
    }*/
}
/*
@Composable
fun ShowResultDialog(gameResult: GameResult, navController: NavController) {
    if (gameResult != GameResult.InProgress) {
        AlertDialog(
            onDismissRequest = { /* No hacer nada al descartar */ },
            title = { Text("S'ha acabat el joc!") },
            text = { Text("Resultat: $gameResult") },
            confirmButton = {
                Button(onClick = { navController.navigate("results") }) {
                    Text("Veure resultats")
                }
            }
        )
    }
}*/


@Composable
fun GameBoard(viewModel: GameViewModel) {
    val boardSize by viewModel.gridOption.collectAsState()
    BoxWithConstraints(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()) {
        val totalSpacerWidth = (boardSize - 1) * 1.dp  // Espacio total ocupado por Spacers
        val gridSize = maxWidth - totalSpacerWidth     // Ajusta el tamaño máximo del tablero
        val cellSize = (gridSize / boardSize)   // Directamente dividiendo Dp por Int

        Column {
            for (y in 0 until boardSize) {
                Row {
                    for (x in 0 until boardSize) {
                        GameCell(viewModel, x, y, viewModel.board.cells[y][x], cellSize)
                        if (x < boardSize - 1) {
                            Spacer(modifier = Modifier.width(1.dp))  // Agrega espaciadores entre las casillas, excepto después de la última
                        }
                    }
                    if (y < boardSize - 1) {
                        Spacer(modifier = Modifier.height(4.dp))  // Agrega espaciadores entre las filas, excepto después de la última
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun GameCell(viewModel: GameViewModel, x: Int, y: Int, cell: Cell, size: Dp) {
    Surface(
        modifier = Modifier
            .size(size)
            .padding(1.dp)
            .combinedClickable(
                onClick = { viewModel.onCellClicked(x, y) },
                onLongClick = { viewModel.toggleFlag(x, y) }
            ),
        color = if (cell.isRevealed) {
            if (cell.hasBomb) {
                AppColors.SecondaryButton
            } else AppColors.UncoveredCells
        } else AppColors.CoveredCells,
        contentColor = if (cell.isRevealed && cell.bombsNearby > 0) getNumberColor(cell.bombsNearby) else Color.White
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = getCellLabel(cell),
                color = if (cell.isRevealed && cell.bombsNearby > 0) getNumberColor(cell.bombsNearby) else Color.White
            )
        }
    }
}

fun calculateTotalBombs(gridSize: Int, bombPercentage: Int): Int {
    return (gridSize * gridSize * bombPercentage / 100)
}

fun getCellLabel(cell: Cell): String {
    return when {
        cell.isRevealed -> if (cell.hasBomb) "💣" else if (cell.bombsNearby > 0) cell.bombsNearby.toString() else ""
        cell.isFlagged -> "🚩"
        else -> ""
    }
}

fun getNumberColor(number: Int): Color {
    return when (number) {
        1 -> AppColors.Number1
        2 -> AppColors.Number2
        3 -> AppColors.Number3
        4 -> AppColors.Number4
        5 -> AppColors.Number5
        6 -> AppColors.Number6
        7 -> AppColors.Number7
        8 -> AppColors.Number8
        else -> Color.Unspecified
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Preview(showBackground = false)
@Composable
fun PreviewGameScreen() {
    PescaminesTheme {
        val mockNavController = rememberNavController()
        val gameViewModel = GameViewModel()
        GameScreen(navController = mockNavController, viewModel = gameViewModel)
    }
}



