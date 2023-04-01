package com.dicoding.core.data.source.local.entity.developer

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dicoding.core.domain.model.developer.DeveloperModel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "developers")
data class DeveloperEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int?,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "slug")
    val slug: String,

    @ColumnInfo(name = "games_count")
    val gamesCount: Int,

    @ColumnInfo(name = "image_background")
    val imageBackground: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false,
): Parcelable {
    fun toModel() : DeveloperModel =
        DeveloperModel(
            id ?: 0,
            name,
            slug,
            gamesCount,
            imageBackground,
            isFavorite
        )
}