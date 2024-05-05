import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pescamines.model.Cell
import com.example.pescamines.viewmodel.GameViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.times
import androidx.navigation.compose.rememberNavController
import com.example.pescamines.ui.theme.AppColors
import com.example.pescamines.ui.theme.PescaminesTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.pointer.pointerInteropFilter
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(navController: NavController, viewModel: GameViewModel) {
    val timeRemaining by viewModel.timeRemaining.collectAsState()
    val bombPercentage by viewModel.bombPercentage.collectAsState()
    val gridValue by viewModel.gridOption.collectAsState()
    val timeEnabled by viewModel.timerEnabled.collectAsState()
    val totalBombs = calculateTotalBombs(gridValue, bombPercentage)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Configuració")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.resetGame() }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Reiniciar partida")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .fillMaxSize()) {
            Spacer(modifier = Modifier.height(60.dp))
            GameBoard(viewModel)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(timeEnabled){
                    Text("Tiempo restante: $timeRemaining s")
                }
                Text("Bombas: $totalBombs ($bombPercentage%)")
            }
        }
    }
}

@Composable
fun GameBoard(viewModel: GameViewModel) {
    val boardSize by viewModel.gridOption.collectAsState()
    BoxWithConstraints(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
        val totalSpacerWidth = (boardSize - 1) * 4.dp  // Espacio total ocupado por Spacers
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
        color = if (cell.isRevealed)
            if (cell.hasBomb) AppColors.Number8 else AppColors.UncoveredCells
        else AppColors.CoveredCells,
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
    return (gridSize * gridSize * bombPercentage / 100).toInt()
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

@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    PescaminesTheme {
        val mockNavController = rememberNavController()
        val gameViewModel = GameViewModel()
        GameScreen(navController = mockNavController, viewModel = gameViewModel)
    }
}



