package com.example.pescamines.data

import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    fun getAllGamesStream(): Flow<List<Game>>

    fun getItemStream(id: Int): Flow<Game?>

    suspend fun insertGame(game: Game)

    suspend fun deleteGame(game: Game)

    suspend fun updateGame(game: Game)
}