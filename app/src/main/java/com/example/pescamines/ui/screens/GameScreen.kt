import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
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
import androidx.navigation.compose.rememberNavController
import com.example.pescamines.ui.theme.AppColors
import com.example.pescamines.ui.theme.PescaminesTheme

@Composable
fun GameScreen(navController: NavController, viewModel: GameViewModel) {
    val timeRemaining = viewModel.timeRemaining.collectAsState()
    val timerEnabled = viewModel.timerEnabled.collectAsState()
    val boardSize = viewModel.gridOption.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        HeaderSection(navController, timerEnabled.value, timeRemaining.value)
        Spacer(modifier = Modifier.height(16.dp))
        GameBoard(viewModel, boardSize.value)
    }
}

@Composable
fun HeaderSection(navController: NavController, timerEnabled: Boolean, timeElapsed: Int) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Button(onClick = { /* Implementar lógica para volver a configuración */ }) {
            Text("Configuración")
        }
        Button(onClick = { /* Implementar lógica para reiniciar juego */ }) {
            Text("Reiniciar")
        }
        Button(onClick = { navController.navigate("results") /* Navegar a resultados */ }) {
            Text("Finalizar")
        }
    }
    if (timerEnabled) {
        Text("Tiempo restante: $timeElapsed segundos")
    }
}
@Composable
fun GameBoard(viewModel: GameViewModel, boardSize: Int) {
    val maxSize = 300.dp  // Definir el tamaño máximo del tablero
    BoxWithConstraints(modifier = Modifier.padding(16.dp)) {
        val size = minOf(maxWidth, maxHeight, maxSize)  // Usa el menor tamaño para mantener el tablero cuadrado y dentro del límite
        val cellSize = size / boardSize

        Column {
            for (y in 0 until boardSize) {
                Row {
                    for (x in 0 until boardSize) {
                        val cell = viewModel.board.cells[y][x]
                        GameCell(viewModel, x, y, cell, cellSize)
                    }
                }
            }
        }
    }
}

@Composable
fun GameCell(viewModel: GameViewModel, x: Int, y: Int, cell: Cell, size: Dp) {
    Button(
        onClick = { viewModel.onCellClicked(x, y) },
        modifier = Modifier
            .size(size)
            .background(
                color = if (cell.isRevealed) AppColors.UncoveredCells else AppColors.CoveredCells
            ),
        contentPadding = PaddingValues(0.dp)
    ) {
        if (cell.isRevealed) {
            val textColor = if (cell.bombsNearby > 0) getNumberColor(cell.bombsNearby) else AppColors.ColorTypography
            Text(
                text = getCellLabel(cell),
                color = textColor,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
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



