package com.example.pescamines.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class Game(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val player: String,
    val temporitzador: Boolean,
    val temps_restant: Int,
    val grid_size: Int,
    val percentage: Int
    )