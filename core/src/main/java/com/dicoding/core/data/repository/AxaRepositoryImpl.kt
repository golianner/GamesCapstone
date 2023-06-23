package com.dicoding.core.data.repository

import com.dicoding.core.data.NetworkBoundResource
import com.dicoding.core.data.Resource
import com.dicoding.core.data.source.local.data_source.LocalPostSource
import com.dicoding.core.data.source.remote.data_source.RemoteAxaSource
import com.dicoding.core.data.source.remote.network.ApiResponse
import com.dicoding.core.data.source.remote.response.axa_test.AxaTestResponse
import com.dicoding.core.data.source.remote.response.developer.DeveloperResponse
import com.dicoding.core.domain.model.axa_test.AxaTestModel
import com.dicoding.core.domain.model.developer.DeveloperModel
import com.dicoding.core.domain.repository.AxaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class AxaRepositoryImpl(
    private val localDataSource: LocalPostSource,
    private val remoteDataSource: RemoteAxaSource
): AxaRepository {
    override fun getPosts(): Flow<Resource<List<AxaTestModel>>> =
        object : NetworkBoundResource<List<AxaTestModel>, List<AxaTestResponse>>() {
            override fun loadFromDB(): Flow<List<AxaTestModel>> {
                return localDataSource.getAll().map { list ->
                    list.map {
                        it.toModel()
                    }
                }
            }

            override fun shouldFetch(data: List<AxaTestModel>?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<List<AxaTestResponse>>> =
                remoteDataSource.getPosts()

            override suspend fun saveCallResult(data: List<AxaTestResponse>) {
                data.map {
                    localDataSource.insert(it.toEntity())
                }
            }
        }.asFlow()

}