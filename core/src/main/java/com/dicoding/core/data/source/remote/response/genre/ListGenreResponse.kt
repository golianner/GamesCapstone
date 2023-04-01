package com.dicoding.core.data.source.remote.response.genre

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListGenreResponse(
    @field:SerializedName("results")
    val list: List<GenresResponse>
): Parcelable
