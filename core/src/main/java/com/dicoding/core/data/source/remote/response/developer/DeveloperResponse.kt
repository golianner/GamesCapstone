package com.dicoding.core.data.source.remote.response.developer


import android.os.Parcelable
import com.dicoding.core.data.source.local.entity.developer.DeveloperEntity
import com.dicoding.core.data.source.local.entity.developer.PartialDeveloperEntity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeveloperResponse(
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
    fun toPartialEntity() : PartialDeveloperEntity =
        PartialDeveloperEntity(
            id,
            name,
            slug,
            gamesCount,
            imageBackground
        )

    fun toEntity() : DeveloperEntity =
        DeveloperEntity(
            id,
            name,
            slug,
            gamesCount,
            imageBackground
        )
}