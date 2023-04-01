package com.dicoding.core.data.source.local.data_source

import com.dicoding.core.data.source.local.entity.game.GameEntity
import com.dicoding.core.data.source.local.entity.game.PartialGameEntity
import com.dicoding.core.data.source.local.room.dao.GameDAO
import kotlinx.coroutines.flow.Flow

class LocalGameSource(private val dao: GameDAO) {
    fun getAll(): Flow<List<GameEntity>> = dao.getAllData()

    fun getFavorite(): Flow<List<GameEntity>> = dao.getAllFavorite()

    suspend fun update(entity: PartialGameEntity) = dao.updateData(entity)

    suspend fun insert(entity: GameEntity) = dao.insertData(entity)

    fun setFavorite(entity: GameEntity, newState: Boolean){
        entity.isFavorite = newState
        dao.updateFavorite(entity)
    }
}