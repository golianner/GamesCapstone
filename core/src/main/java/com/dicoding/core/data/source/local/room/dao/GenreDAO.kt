package com.dicoding.core.data.source.local.room.dao

import androidx.room.*
import com.dicoding.core.data.source.local.entity.genre.GenreEntity
import com.dicoding.core.data.source.local.entity.genre.PartialGenreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDAO {
    @Query("SELECT * FROM genres")
    fun getAllData(): Flow<List<GenreEntity>>

    @Query("SELECT * FROM genres WHERE isFavorite = 1")
    fun getAllFavorites(): Flow<List<GenreEntity>>

    @Update(entity = GenreEntity::class)
    suspend fun updateData(genreEntity: PartialGenreEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(genres: GenreEntity): Long

    @Update
    fun updateFavorite(genres: GenreEntity)
}