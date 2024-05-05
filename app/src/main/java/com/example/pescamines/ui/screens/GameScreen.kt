package com.example.pescamines.ui.screens
/*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.pescamines.model.Cell
import com.example.pescamines.viewmodel.GameViewModel

@Composable
fun GameScreen(viewModel: GameViewModel, navController: NavController) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        for (y in 0 until viewModel.width) {
            Row(horizontalArrangement = Arrangement.Center) {
                for (x in 0 until viewModel.width) {
                    CellComposable(cell = viewModel.board.cells[y][x]) {
                        viewModel.onCellClicked(x, y)
                        if (viewModel.gameOver.value) {
                            navController.navigate("results")  // Navega a la pantalla de resultats
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CellComposable(cell: Cell, onCellClicked: () -> Unit) {
    Button(onClick = onCellClicked) {
        Text(if (cell.isRevealed) (if (cell.hasBomb) "💣" else if (cell.bombsNearby > 0) cell.bombsNearby.toString() else "") else "")
    }
}

*/
