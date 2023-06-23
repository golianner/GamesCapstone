package com.dicoding.core.data.source.remote.network

import com.dicoding.core.data.source.remote.response.axa_test.AxaTestResponse
import retrofit2.http.GET

interface AxaService {
    @GET("posts")
    suspend fun getAxaTest(): List<AxaTestResponse>

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
}