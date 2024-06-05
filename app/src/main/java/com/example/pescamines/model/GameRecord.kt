package com.example.pescamines.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_records")
data class GameRecord(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userName: String,
    val endTime: Long,
    val gridSize: Int,
    val bombPercentage: Int,
    val numBombs: Int,
    val timerEnabled: Boolean,
    val timeTaken: Int,
    val bombLocationX: Int,
    val bombLocationY: Int,
    val gameResult: String
)
