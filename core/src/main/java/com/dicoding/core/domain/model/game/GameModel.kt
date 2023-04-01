package com.dicoding.core.domain.model.game

import android.os.Parcelable
import com.dicoding.core.data.source.local.entity.game.GameEntity
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameModel(
    val id: Int,
    val name: String,
    val slug: String,
    val released: String,
    val backgroundImage: String,
    val rating: Double,
    val ratingsCount: Int,
    val genres: List<GenreModel>,
    val shortScreenshots: List<ShortScreenshotModel>,
    val isFavorite: Boolean = false
): Parcelable {
    fun toEntity(): GameEntity =
        GameEntity(
            id,
            name,
            slug,
            released,
            backgroundImage,
            rating,
            ratingsCount,
            Gson().toJson(genres),
            Gson().toJson(shortScreenshots)
        )
}
