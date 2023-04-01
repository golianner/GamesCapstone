package com.dicoding.core.data.source.local.room.dao

import androidx.room.*
import com.dicoding.core.data.source.local.entity.developer.DeveloperEntity
import com.dicoding.core.data.source.local.entity.developer.PartialDeveloperEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeveloperDAO {
    // Developer
    @Query("SELECT * FROM developers")
    fun getAllData(): Flow<List<DeveloperEntity>>

    @Query("SELECT * FROM developers WHERE isFavorite = 1")
    fun getAllFavorites(): Flow<List<DeveloperEntity>>

    @Update(entity = DeveloperEntity::class)
    suspend fun updateData(developerEntity: PartialDeveloperEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(developers: DeveloperEntity): Long

    @Update
    fun updateFavorite(developers: DeveloperEntity)
}