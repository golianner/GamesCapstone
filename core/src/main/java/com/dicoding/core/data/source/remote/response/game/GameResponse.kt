package com.dicoding.core.data.source.remote.response.game

import com.dicoding.core.data.source.local.entity.game.GameEntity
import com.dicoding.core.data.source.local.entity.game.PartialGameEntity
import com.dicoding.core.domain.model.game.GameModel
import com.dicoding.core.domain.model.game.GenreModel
import com.dicoding.core.domain.model.game.ShortScreenshotModel
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class GameResponse(
    @field:SerializedName("background_image")
    val backgroundImage: String?,
    @field:SerializedName("genres")
    val genreResponses: List<GenreResponse>,
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("rating")
    val rating: Double,
    @field:SerializedName("ratings_count")
    val ratingsCount: Int,
    @field:SerializedName("released")
    val released: String?,
    @field:SerializedName("short_screenshots")
    val shortScreenshotResponses: List<ShortScreenshotResponse>? = emptyList(),
    @field:SerializedName("slug")
    val slug: String
) {
    fun toPartialEntity(): PartialGameEntity =
        PartialGameEntity(
            id,
            name,
            slug,
            released ?: "",
            backgroundImage ?: "",
            rating,
            ratingsCount,
            Gson().toJson(genreResponses),
            Gson().toJson(shortScreenshotResponses)
        )

    fun toEntity(): GameEntity =
        GameEntity(
            id,
            name,
            slug,
            released ?: "",
            backgroundImage ?: "",
            rating,
            ratingsCount,
            Gson().toJson(genreResponses),
            Gson().toJson(shortScreenshotResponses)
        )

    fun toDomainModel(): GameModel {
        return GameModel(
            id,
            name,
            slug,
            released ?: "",
            backgroundImage ?: "",
            rating,
            ratingsCount,
            toGenreModel(genreResponses),
            toShortScreenshotModel(shortScreenshotResponses)
        )
    }

    private fun toGenreModel(data: List<GenreResponse>): List<GenreModel> =
        data.map {
            GenreModel(it.id, it.name)
        }

    private fun toShortScreenshotModel(data: List<ShortScreenshotResponse>?): List<ShortScreenshotModel> =
        data?.map {
            ShortScreenshotModel(it.id, it.image)
        } ?: emptyList()
}