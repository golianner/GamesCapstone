package com.dicoding.core.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class FilterType(val type: String, val identifier: String) : Parcelable {
    data class Developer(val slug: String) : FilterType(DEVELOPER, slug)
    data class Genre(val slug: String) : FilterType(GENRE, slug)

    companion object {
        private const val DEVELOPER = "Developer"
        private const val GENRE = "Genre"
    }
}
