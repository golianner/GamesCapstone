package com.dicoding.core.data.source.remote.network

import com.dicoding.core.BuildConfig
import com.dicoding.core.data.source.remote.response.axa_test.AxaTestResponse
import com.dicoding.core.data.source.remote.response.developer.ListDeveloperResponse
import com.dicoding.core.data.source.remote.response.game.ListGameResponse
import com.dicoding.core.data.source.remote.response.genre.ListGenreResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("games$API_KEY")
    suspend fun getGames(): ListGameResponse

    @GET("games$API_KEY")
    suspend fun searchGamesAutoComplete(@Query("search") search: String, @Query("page") page: Int = 1,
                                        @Query("page_size") page_size: Int = 5): ListGameResponse

    @GET("developers$API_KEY")
    suspend fun getDevelopers(): ListDeveloperResponse

    @GET("genres$API_KEY")
    suspend fun getGenres(): ListGenreResponse

    @GET("games$API_KEY")
    suspend fun getGamesFilterDeveloper(@Query("developers") slug: String): ListGameResponse

    @GET("games$API_KEY")
    suspend fun getGamesFilterGenre(@Query("genres") slug: String): ListGameResponse

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
        const val BASE_URL = BuildConfig.BASE_URL
    }
}