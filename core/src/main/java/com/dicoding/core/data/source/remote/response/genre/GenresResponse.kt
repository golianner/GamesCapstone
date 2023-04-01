package com.dicoding.core.data.source.remote.response.genre

import android.os.Parcelable
import com.dicoding.core.data.source.local.entity.genre.GenreEntity
import com.dicoding.core.data.source.local.entity.genre.PartialGenreEntity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenresResponse(
    @field:SerializedName("games_count")
    val gamesCount: Int,
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("image_background")
    val imageBackground: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("slug")
    val slug: String
): Parcelable {
    fun toPartialEntity() : PartialGenreEntity =
        PartialGenreEntity(
            id,
            name,
            slug,
            gamesCount,
            imageBackground
        )

    fun toEntity() : GenreEntity =
        GenreEntity(
            id,
            name,
            slug,
            gamesCount,
            imageBackground
        )
}
