package com.dicoding.core.data.source.local.entity.game

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dicoding.core.domain.model.game.GameModel
import com.dicoding.core.domain.model.game.GenreModel
import com.dicoding.core.domain.model.game.ShortScreenshotModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int?,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "slug")
    val slug: String,

    @ColumnInfo(name = "released")
    val released: String,

    @ColumnInfo(name = "background_image")
    val backgroundImage: String,

    @ColumnInfo(name = "rating")
    val rating: Double,

    @ColumnInfo(name = "ratings_count")
    val ratingsCount: Int,

    @ColumnInfo(name = "genres")
    val genres: String,

    @ColumnInfo(name = "short_screenshots")
    val shortScreenshots: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false,
): Parcelable {
    fun toModel(): GameModel {
        // Create type token for JSONArray
        val typeTokenGenre = object : TypeToken<List<GenreEntity>>() {}.type
        val typeTokenSS = object : TypeToken<List<ShortScreenshotEntity>>() {}.type
        // Generate List from JSONArray
        val genresData = Gson().fromJson<List<GenreEntity>>(genres, typeTokenGenre)
        val shortSSData = Gson().fromJson<List<ShortScreenshotEntity>>(shortScreenshots, typeTokenSS)
        // Change GameEntity to GameModel (domain)
        return GameModel(
            id ?: 0,
            name,
            slug,
            released,
            backgroundImage,
            rating,
            ratingsCount,
            toGenreModel(genresData),
            toShortScreenshotModel(shortSSData),
            isFavorite
        )
    }

    private fun toGenreModel(data: List<GenreEntity>?): List<GenreModel> =
        data!!.map {
            GenreModel(it.id, it.name)
        }

    private fun toShortScreenshotModel(data: List<ShortScreenshotEntity>?): List<ShortScreenshotModel> =
        data?.map {
            ShortScreenshotModel(it.id, it.image)
        } ?: emptyList()
}

@Parcelize
private data class GenreEntity(
    val id: Int,
    val name: String
): Parcelable

@Parcelize
private data class ShortScreenshotEntity(
    val id: Int,
    val image: String
): Parcelable