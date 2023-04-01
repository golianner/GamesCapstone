package com.dicoding.core.data.source.local.data_source

import com.dicoding.core.data.source.local.entity.genre.GenreEntity
import com.dicoding.core.data.source.local.entity.genre.PartialGenreEntity
import com.dicoding.core.data.source.local.room.dao.GenreDAO
import kotlinx.coroutines.flow.Flow

class LocalGenreSource(private val dao: GenreDAO) {
    fun getAll(): Flow<List<GenreEntity>> = dao.getAllData()

    fun getFavorite(): Flow<List<GenreEntity>> = dao.getAllFavorites()

    suspend fun update(entity: PartialGenreEntity) = dao.updateData(entity)

    suspend fun insert(entity: GenreEntity) = dao.insertData(entity)

    fun setFavorite(entity: GenreEntity, newState: Boolean){
        entity.isFavorite = newState
        dao.updateFavorite(entity)
    }
}