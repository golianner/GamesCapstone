package com.dicoding.core.data.source.local.room.dao

import androidx.room.*
import com.dicoding.core.data.source.local.entity.game.GameEntity
import com.dicoding.core.data.source.local.entity.game.PartialGameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDAO {
    // Games
    @Query("SELECT * FROM games")
    fun getAllData(): Flow<List<GameEntity>>

    @Query("SELECT * FROM games WHERE isFavorite = 1")
    fun getAllFavorite(): Flow<List<GameEntity>>

    @Update(entity = GameEntity::class)
    suspend fun updateData(gameEntity: PartialGameEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(games: GameEntity): Long

    @Update
    fun updateFavorite(games: GameEntity)
}