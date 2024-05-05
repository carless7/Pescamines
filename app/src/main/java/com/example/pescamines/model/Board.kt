package com.example.pescamines.model


class Board(private val width: Int) {
    val cells = Array(width) { Array(width) { Cell() } }

    fun initialize(bombManager: BombManager, numberCalculator: NumberCalculator) {
        bombManager.placeBombs(cells)
        numberCalculator.calculateNumbers(cells)
    }

    fun revealCell(x: Int, y: Int) {
        if (!cells[y][x].isRevealed && !cells[y][x].isFlagged) {
            cells[y][x].isRevealed = true
            if (cells[y][x].bombsNearby == 0 && !cells[y][x].hasBomb) {
                // Recursively reveal adjacent cells
                for (dy in -1..1) {
                    for (dx in -1..1) {
                        val nx = x + dx
                        val ny = y + dy
                        if (nx in 0 until width && ny in 0 until width) {
                            revealCell(nx, ny)  // Recursive call
                        }
                    }
                }
            }
        }
    }

    fun toggleFlag(x: Int, y: Int) {
        if (!cells[y][x].isRevealed) {  // Solo se puede marcar/desmarcar celdas no reveladas
            cells[y][x].isFlagged = !cells[y][x].isFlagged
        }
    }

    fun checkGameStatus(): GameStatus {
        var allSafeCellsRevealed = true
        for (row in cells) {
            for (cell in row) {
                if (cell.hasBomb && cell.isRevealed) {
                    return GameStatus.Lost  // El jugador reveló una bomba
                }
                if (!cell.hasBomb && !cell.isRevealed) {
                    allSafeCellsRevealed = false
                }
            }
        }
        return if (allSafeCellsRevealed) GameStatus.Won else GameStatus.InProgress
    }

    // Método para comprobar si el jugador ha ganado
    fun checkIfWon(): Boolean {
        for (row in cells) {
            for (cell in row) {
                // Si hay alguna celda que no tenga bomba y no esté revelada, aún no se ha ganado
                if (!cell.hasBomb && !cell.isRevealed) {
                    return false
                }
            }
        }
        // Si todas las celdas sin bombas están reveladas, el jugador ha ganado
        return true
    }
}