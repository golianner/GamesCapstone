package com.dicoding.core.data.source.local.data_source

import com.dicoding.core.data.source.local.entity.developer.DeveloperEntity
import com.dicoding.core.data.source.local.entity.developer.PartialDeveloperEntity
import com.dicoding.core.data.source.local.room.dao.DeveloperDAO
import kotlinx.coroutines.flow.Flow

class LocalDeveloperSource(private val dao: DeveloperDAO) {
    fun getAll(): Flow<List<DeveloperEntity>> = dao.getAllData()

    fun getFavorite(): Flow<List<DeveloperEntity>> = dao.getAllFavorites()

    suspend fun update(entity: PartialDeveloperEntity) = dao.updateData(entity)

    suspend fun insert(entity: DeveloperEntity) = dao.insertData(entity)

    fun setFavorite(entity: DeveloperEntity, newState: Boolean){
        entity.isFavorite = newState
        dao.updateFavorite(entity)
    }
}