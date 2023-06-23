package com.dicoding.core.data.source.local.entity.axa_test

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dicoding.core.domain.model.axa_test.AxaTestModel
import com.dicoding.core.domain.model.developer.DeveloperModel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "body")
    val body: String,

    @ColumnInfo(name = "userId")
    val userId: Int,
): Parcelable {
    fun toModel() : AxaTestModel =
        AxaTestModel(body, id, title, userId)
}