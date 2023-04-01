package com.dicoding.core.data.source.local.entity.genre

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dicoding.core.domain.model.genre.GenresModel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "genres")
data class GenreEntity(
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
    fun toModel() : GenresModel =
        GenresModel(
            id ?: 0,
            name,
            slug,
            gamesCount,
            imageBackground,
            isFavorite
        )
}
