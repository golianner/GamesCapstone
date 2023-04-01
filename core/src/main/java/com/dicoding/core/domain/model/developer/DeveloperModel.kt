package com.dicoding.core.domain.model.developer

import android.os.Parcelable
import com.dicoding.core.data.source.local.entity.developer.DeveloperEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeveloperModel(
    val id: Int,
    val name: String,
    val slug: String,
    val gamesCount: Int,
    val backgroundImage: String,
    val isFavorite: Boolean = false
): Parcelable {
    fun toEntity() : DeveloperEntity =
        DeveloperEntity(
            id,
            name,
            slug,
            gamesCount,
            backgroundImage
        )
}
