package com.example.pescamines.model

class NumberCalculator {
    fun calculateNumbers(cells: Array<Array<Cell>>) {
        for (y in cells.indices) {
            for (x in cells[y].indices) {
                if (!cells[y][x].hasBomb) {
                    cells[y][x].bombsNearby = getBombsNearby(x, y, cells)
                }
            }
        }
    }

    private fun getBombsNearby(x: Int, y: Int, cells: Array<Array<Cell>>): Int {
        var count = 0
        for (dy in -1..1) {
            for (dx in -1..1) {
                if (dx == 0 && dy == 0) continue
                val nx = x + dx
                val ny = y + dy
                if (nx >= 0 && nx < cells[0].size && ny >= 0 && ny < cells.size && cells[ny][nx].hasBomb) {
                    count++
                }
            }
        }
        return count
    }
}