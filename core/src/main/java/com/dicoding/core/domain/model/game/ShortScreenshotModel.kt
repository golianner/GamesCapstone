package com.dicoding.core.domain.model.game

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShortScreenshotModel(
    val id: Int,
    val image: String,
): Parcelable
