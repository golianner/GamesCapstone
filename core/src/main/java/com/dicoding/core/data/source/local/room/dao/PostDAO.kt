package com.dicoding.core.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dicoding.core.data.source.local.entity.axa_test.PostEntity
import com.dicoding.core.data.source.local.entity.game.GameEntity
import com.dicoding.core.data.source.local.entity.game.PartialGameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDAO {
    @Query("SELECT * FROM posts")
    fun getAllData(): Flow<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(postEntity: PostEntity)
}