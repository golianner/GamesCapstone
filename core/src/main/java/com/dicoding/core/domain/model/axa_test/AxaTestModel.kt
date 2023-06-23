package com.dicoding.core.domain.model.axa_test


import android.os.Parcelable
import com.dicoding.core.data.source.remote.response.axa_test.AxaTestResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AxaTestModel(
    @SerializedName("body")
    val body: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("userId")
    val userId: Int
): Parcelable