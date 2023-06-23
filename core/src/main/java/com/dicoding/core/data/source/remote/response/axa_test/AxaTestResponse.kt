package com.dicoding.core.data.source.remote.response.axa_test


import android.os.Parcelable
import com.dicoding.core.data.source.local.entity.axa_test.PostEntity
import com.dicoding.core.domain.model.axa_test.AxaTestModel
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AxaTestResponse(
    @field:SerializedName("body")
    val body: String,
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("userId")
    val userId: Int
): Parcelable {
    fun toModel() : AxaTestModel{
        return AxaTestModel(
            body, id, title, userId
        )
    }
    fun toEntity() : PostEntity {
        return PostEntity(id, title, body, userId)
    }
}