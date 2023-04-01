package com.dicoding.core.domain.model.genre

import android.os.Parcelable
import com.dicoding.core.data.source.local.entity.genre.GenreEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenresModel(
    val id: Int,
    val name: String,
    val slug: String,
    val gamesCount: Int,
    val backgroundImage: String,
    val isFavorite: Boolean = false
): Parcelable {
    fun toEntity() : GenreEntity =
        GenreEntity(
            id,
            name,
            slug,
            gamesCount,
            backgroundImage
        )
}
