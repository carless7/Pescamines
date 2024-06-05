package com.example.pescamines.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

class MockGameDao : GameDao {
    private val games = MutableStateFlow<List<GameRecord>>(emptyList())

    override suspend fun insert(game: GameRecord) {
        games.value += game
    }

    override fun getAllGames(): Flow<List<GameRecord>> = games

    override fun getGameById(gameId: Long): Flow<GameRecord?> {
        return flowOf(games.value.find { it.id == gameId })
    }
}
