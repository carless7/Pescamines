package com.example.pescamines.data

import kotlinx.coroutines.flow.Flow

class OfflineGamesRepository(private val gameDao: GameDao) : GamesRepository {
    override fun getAllGamesStream(): Flow<List<Game>> = gameDao.getAllItems()

    override fun getItemStream(id: Int): Flow<Game?> = gameDao.getGame(id)

    override suspend fun insertGame(game: Game) = gameDao.insert(game)

    override suspend fun deleteGame(game: Game) = gameDao.delete(game)

    override suspend fun updateGame(game: Game) = gameDao.update(game)
}