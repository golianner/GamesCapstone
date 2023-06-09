package com.dicoding.core.data.source.remote.response.game


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreResponse(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String
): Parcelable