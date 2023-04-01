package com.dicoding.core.data.repository

import com.dicoding.core.data.NetworkBoundResource
import com.dicoding.core.data.Resource
import com.dicoding.core.data.source.local.data_source.LocalDeveloperSource
import com.dicoding.core.data.source.remote.data_source.RemoteDeveloperSource
import com.dicoding.core.data.source.remote.network.ApiResponse
import com.dicoding.core.data.source.remote.response.developer.DeveloperResponse
import com.dicoding.core.domain.model.developer.DeveloperModel
import com.dicoding.core.domain.repository.DeveloperRepository
import com.dicoding.core.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DeveloperRepositoryImpl(
    private val localDataSource: LocalDeveloperSource,
    private val remoteDataSource: RemoteDeveloperSource,
    private val appExecutors: AppExecutors
): DeveloperRepository {
    override fun getAll(refresh: Boolean): Flow<Resource<List<DeveloperModel>>> =
        object : NetworkBoundResource<List<DeveloperModel>, List<DeveloperResponse>>() {
            override fun loadFromDB(): Flow<List<DeveloperModel>> {
                return localDataSource.getAll().map { list ->
                    list.map {
                        it.toModel()
                    }
                }
            }

            override fun shouldFetch(data: List<DeveloperModel>?): Boolean {
                var fetch = data == null || data.isEmpty()
                if (refresh){
                    fetch = true
                }
                return fetch
            }

            override suspend fun createCall(): Flow<ApiResponse<List<DeveloperResponse>>> =
                remoteDataSource.getAll()

            override suspend fun saveCallResult(data: List<DeveloperResponse>) {
                data.map {
                    val entity = it.toEntity()
                    val rowId = localDataSource.insert(entity)
                    if (rowId == -1L){
                        localDataSource.update(it.toPartialEntity())
                    }
                }
            }
        }.asFlow()

    override fun getFavorites(): Flow<List<DeveloperModel>> {
        return localDataSource.getFavorite().map { list ->
            list.map {
                it.toModel()
            }
        }
    }

    override fun setFavorite(developerModel: DeveloperModel, state: Boolean) {
        val developerEntity = developerModel.toEntity()
        appExecutors.diskIO().execute {
            localDataSource.setFavorite(developerEntity, state)
        }
    }
}