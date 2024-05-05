package com.example.pescamines.model

data class Cell(
    var hasBomb: Boolean = false,
    var bombsNearby: Int = 0,
    var isRevealed: Boolean = false,
    var isFlagged: Boolean = false
)