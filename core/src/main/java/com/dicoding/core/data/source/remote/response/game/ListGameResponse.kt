package com.dicoding.core.data.source.remote.response.game

import com.google.gson.annotations.SerializedName

data class ListGameResponse(
    @field:SerializedName("results")
    val list: List<GameResponse>
)
