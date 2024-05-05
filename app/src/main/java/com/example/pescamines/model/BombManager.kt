package com.example.pescamines.model

class BombManager(private val numBombs: Int) {
    fun placeBombs(cells: Array<Array<Cell>>) {
        var bombsPlaced = 0
        while (bombsPlaced < numBombs) {
            val x = (Math.random() * cells[0].size).toInt()
            val y = (Math.random() * cells[1].size).toInt()
            if (!cells[y][x].hasBomb) {
                cells[y][x].hasBomb = true
                bombsPlaced++
            }
        }
    }
}