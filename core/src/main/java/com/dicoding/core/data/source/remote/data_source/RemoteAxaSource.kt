package com.dicoding.core.data.source.remote.data_source

import android.util.Log
import com.dicoding.core.data.source.remote.network.ApiResponse
import com.dicoding.core.data.source.remote.network.AxaService
import com.dicoding.core.data.source.remote.response.axa_test.AxaTestResponse
import com.dicoding.core.data.source.remote.response.developer.DeveloperResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RemoteAxaSource(private val apiService: AxaService) {
    suspend fun getPosts(): Flow<ApiResponse<List<AxaTestResponse>>> {
        return flow {
            try {
                val response = apiService.getAxaTest()
                println("data ${response.size}")
                if (response.isNotEmpty()){
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}