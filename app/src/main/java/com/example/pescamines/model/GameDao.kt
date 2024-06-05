package com.example.pescamines.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pescamines.model.GameRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Insert
    suspend fun insert(game: GameRecord)

    @Query("SELECT * FROM game_records ORDER BY endTime DESC")
    fun getAllGames(): Flow<List<GameRecord>>

    @Query("SELECT * FROM game_records WHERE id = :gameId")
    fun getGameById(gameId: Long): Flow<GameRecord?>
}
