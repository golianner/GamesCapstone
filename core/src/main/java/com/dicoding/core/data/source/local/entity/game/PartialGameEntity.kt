package com.dicoding.core.data.source.local.entity.game

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class PartialGameEntity(
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
): Parcelable
