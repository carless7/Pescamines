package com.example.pescamines.data

import android.content.Context


interface GameContainer {
    val gamesRepository: GamesRepository
}
class GameDataContainer(private val context: Context) : GameContainer {
    override val gamesRepository: GamesRepository by lazy {
        OfflineGamesRepository(GameDatabase.getDatabase(context).gameDao())
    }
}