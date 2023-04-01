package com.dicoding.core.domain.model.game

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreModel(
    val id: Int,
    val name: String,
): Parcelable
