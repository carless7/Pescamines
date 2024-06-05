import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pescamines.data.MockGameDao
import com.example.pescamines.data.MockUserPreferencesRepository
import com.example.pescamines.model.Cell
import com.example.pescamines.ui.theme.AppColors
import com.example.pescamines.ui.theme.PescaminesTheme
import com.example.pescamines.ui.theme.jerseyFontFamily
import com.example.pescamines.viewmodel.GameResult
import com.example.pescamines.viewmodel.GameViewModel


@Composable
fun GameScreen(navController: NavController, viewModel: GameViewModel) {
    val configuration = LocalConfiguration.current
    val isTablet = isTabletDevice()

    val timeRemaining by viewModel.timeRemaining.collectAsState()
    val bombPercentage by viewModel.bombPercentage.collectAsState()
    val gridValue by viewModel.gridOption.collectAsState()
    val timeEnabled by viewModel.timerEnabled.collectAsState()
    val totalBombs = calculateTotalBombs(gridValue, bombPercentage)
    val gameResult by viewModel.gameResult.collectAsState()
    val eventLog by viewModel.eventLog.collectAsState()

    Scaffold(
        topBar = { GameTopBar(navController, viewModel) },
        containerColor = AppColors.Background
    ) { padding ->
        BoxWithConstraints(
            modifier = Modifier.padding(padding)
        ) {
            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // Landscape layout
                LandscapeLayout(isTablet, viewModel, timeRemaining, totalBombs, bombPercentage, timeEnabled, eventLog)
            } else {
                // Portrait layout
                PortraitLayout(isTablet, viewModel, timeRemaining, totalBombs, bombPercentage, timeEnabled, eventLog)
            }
        }

        // Gestió de l'estat del joc
        LaunchedEffect(gameResult) {
            if (gameResult != GameResult.InProgress) {
                viewModel.stopTimer()
                navController.navigate("results")
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTopBar(navController: NavController, viewModel: GameViewModel) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "PescaMines",
                fontSize = 48.sp,
                style = TextStyle(
                    shadow = Shadow(
                        color = AppColors.SecondaryButton,
                        offset = Offset(5f, 5f),
                        blurRadius = 10f
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
                    modifier = Modifier.size(128.dp)
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
fun LandscapeLayout(
    isTablet: Boolean,
    viewModel: GameViewModel,
    timeRemaining: Int,
    totalBombs: Int,
    bombPercentage: Int,
    timeEnabled: Boolean,
    eventLog: List<String>
) {
    Row(modifier = Modifier.fillMaxSize()) {
        if (isTablet) {
            // Tablet bi-panel: game on the left, events on the right
            Column(modifier = Modifier.weight(1f)) {
                GameBoard(viewModel, eventLog)
            }
            Column(modifier = Modifier.weight(1f)) {
                GameStatus(timeRemaining, totalBombs, bombPercentage, timeEnabled)
                EventLog(eventLog)
            }

        } else {
            // Smartphone: only the game board
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BoxWithConstraints(modifier = Modifier.weight(1f)) {
                    val maxHeight = maxHeight
                    val maxWidth = maxWidth
                    val boardSize = minOf(maxWidth, maxHeight)

                    Box(
                        modifier = Modifier
                            .size(boardSize)
                            .align(Alignment.Center)
                    ) {
                        GameBoard(viewModel, eventLog)
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(0.3f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GameStatus(timeRemaining, totalBombs, bombPercentage, timeEnabled)
                }
            }
        }
    }
}

@Composable
fun PortraitLayout(
    isTablet: Boolean,
    viewModel: GameViewModel,
    timeRemaining: Int,
    totalBombs: Int,
    bombPercentage: Int,
    timeEnabled: Boolean,
    eventLog: List<String>
) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (isTablet) {
            // Tablet bi-panel: events on top, game on bottom
            BoxWithConstraints(modifier = Modifier.weight(1f)) {
                val maxHeight = maxHeight
                val maxWidth = maxWidth
                val boardSize = minOf(maxWidth, maxHeight)

                Column(
                    modifier = Modifier
                        .size(boardSize)
                        .align(Alignment.Center)
                ) {
                    GameBoard(viewModel, eventLog)
                }
            }
            GameStatus(timeRemaining, totalBombs, bombPercentage, timeEnabled)
            EventLog(eventLog, Modifier.weight(1f).verticalScroll(rememberScrollState()))
        } else {
            // Smartphone: only the game board
            GameBoard(viewModel, eventLog)
            GameStatus(timeRemaining, totalBombs, bombPercentage, timeEnabled)
        }
    }
}

@Composable
fun GameBoard(viewModel: GameViewModel, eventLog: List<String>, modifier: Modifier = Modifier) {
    val boardSize by viewModel.gridOption.collectAsState()

    BoxWithConstraints(modifier = modifier
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {
        val totalSpacerWidth = (boardSize - 1) * 1.dp
        val gridSize = maxWidth - totalSpacerWidth
        val cellSize = (gridSize / boardSize)

        Column {
            for (y in 0 until boardSize) {
                Row {
                    for (x in 0 until boardSize) {
                        GameCell(viewModel, x, y, viewModel.board.cells[y][x], cellSize) {
                            // Afegir esdeveniment al log
                            viewModel.onCellClicked(x, y)
                        }
                        if (x < boardSize - 1) {
                            Spacer(modifier = Modifier.width(1.dp))
                        }
                    }
                    if (y < boardSize - 1) {
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun GameCell(viewModel: GameViewModel, x: Int, y: Int, cell: Cell, size: Dp, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .size(size)
            .padding(1.dp)
            .combinedClickable(
                onClick = {
                    viewModel.onCellClicked(x, y)
                    onClick()
                },
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
                color = if (cell.isRevealed && cell.bombsNearby > 0) getNumberColor(cell.bombsNearby) else Color.White,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(5f, 5f),
                        blurRadius = 10f
                    )
                )
            )
        }
    }
}

@Composable
fun GameStatus(timeRemaining: Int, totalBombs: Int, bombPercentage: Int, timeEnabled: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (timeEnabled) {
            Text("⌛: $timeRemaining s", color = AppColors.ColorTypography)
        }
        Text("\uD83D\uDCA3: $totalBombs ($bombPercentage%)", color = AppColors.ColorTypography)
    }
}

@Composable
fun EventLog(eventLog: List<String>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Text("Event Log:", style = TextStyle(fontSize = 20.sp, color = AppColors.ColorTypography))
        eventLog.forEach { event ->
            Text(event, style = TextStyle(fontSize = 16.sp, color = AppColors.ColorTypography))
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
    val context = LocalContext.current
    val mockNavController = rememberNavController()
    val mockGameDao = MockGameDao()
    val mockUserPreferencesRepository = MockUserPreferencesRepository(context)
    val gameViewModel = GameViewModel(mockGameDao, mockUserPreferencesRepository)

    PescaminesTheme {
        GameScreen(navController = mockNavController, viewModel = gameViewModel)
    }
}
