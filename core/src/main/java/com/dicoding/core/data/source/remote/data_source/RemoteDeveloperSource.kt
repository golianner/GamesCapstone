package com.dicoding.core.data.source.remote.data_source

import android.util.Log
import com.dicoding.core.data.source.remote.network.ApiResponse
import com.dicoding.core.data.source.remote.network.ApiService
import com.dicoding.core.data.source.remote.response.developer.DeveloperResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RemoteDeveloperSource(private val apiService: ApiService) {
    suspend fun getAll(): Flow<ApiResponse<List<DeveloperResponse>>> {
        return flow {
            try {
                val response = apiService.getDevelopers()
                val dataArray = response.list
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.list))
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