package com.dicoding.core.data.source.local.data_source

import com.dicoding.core.data.source.local.entity.axa_test.PostEntity
import com.dicoding.core.data.source.local.entity.game.PartialGameEntity
import com.dicoding.core.data.source.local.entity.genre.GenreEntity
import com.dicoding.core.data.source.local.entity.genre.PartialGenreEntity
import com.dicoding.core.data.source.local.room.dao.GenreDAO
import com.dicoding.core.data.source.local.room.dao.PostDAO
import kotlinx.coroutines.flow.Flow

class LocalPostSource(private val dao: PostDAO) {
    fun getAll(): Flow<List<PostEntity>> = dao.getAllData()
    suspend fun insert(entity: PostEntity) = dao.insertData(entity)
}