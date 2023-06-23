package com.dicoding.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.core.data.source.local.entity.axa_test.PostEntity
import com.dicoding.core.data.source.local.entity.developer.DeveloperEntity
import com.dicoding.core.data.source.local.entity.game.GameEntity
import com.dicoding.core.data.source.local.entity.genre.GenreEntity
import com.dicoding.core.data.source.local.room.dao.DeveloperDAO
import com.dicoding.core.data.source.local.room.dao.GameDAO
import com.dicoding.core.data.source.local.room.dao.GenreDAO
import com.dicoding.core.data.source.local.room.dao.PostDAO

@Database(entities = [GameEntity::class, DeveloperEntity::class, GenreEntity::class, PostEntity::class], version = 4, exportSchema = false)
abstract class GameDatabase: RoomDatabase() {
    abstract fun gameDAO(): GameDAO
    abstract fun developerDAO(): DeveloperDAO
    abstract fun genreDAO(): GenreDAO
    abstract fun postDAO(): PostDAO
}