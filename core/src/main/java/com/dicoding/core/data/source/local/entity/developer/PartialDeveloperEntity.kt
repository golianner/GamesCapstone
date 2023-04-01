package com.dicoding.core.data.source.local.entity.developer

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class PartialDeveloperEntity(
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
): Parcelable
