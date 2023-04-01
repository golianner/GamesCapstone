package com.dicoding.core.data.source.remote.response.developer

import com.google.gson.annotations.SerializedName

data class ListDeveloperResponse(
    @field:SerializedName("results")
    val list: List<DeveloperResponse>
)