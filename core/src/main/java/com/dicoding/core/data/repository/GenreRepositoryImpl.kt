package com.dicoding.core.data.repository

import com.dicoding.core.data.NetworkBoundResource
import com.dicoding.core.data.Resource
import com.dicoding.core.data.source.local.data_source.LocalGenreSource
import com.dicoding.core.data.source.remote.data_source.RemoteGenreSource
import com.dicoding.core.data.source.remote.network.ApiResponse
import com.dicoding.core.data.source.remote.response.genre.GenresResponse
import com.dicoding.core.domain.model.genre.GenresModel
import com.dicoding.core.domain.repository.GenreRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class GenreRepositoryImpl(
    private val localDataSource: LocalGenreSource,
    private val remoteDataSource: RemoteGenreSource
): GenreRepository {
    override fun getAll(refresh: Boolean): Flow<Resource<List<GenresModel>>> =
        object : NetworkBoundResource<List<GenresModel>, List<GenresResponse>>() {
            override fun loadFromDB(): Flow<List<GenresModel>> {
                return localDataSource.getAll().map { list ->
                    list.map {
                        it.toModel()
                    }
                }
            }

            override fun shouldFetch(data: List<GenresModel>?): Boolean {
                var fetch = data == null || data.isEmpty()
                if (refresh){
                    fetch = true
                }
                return fetch
            }

            override suspend fun createCall(): Flow<ApiResponse<List<GenresResponse>>> =
                remoteDataSource.getAll()

            override suspend fun saveCallResult(data: List<GenresResponse>) {
                data.map {
                    val entity = it.toEntity()
                    val rowId = localDataSource.insert(entity)
                    if (rowId == -1L){
                        localDataSource.update(it.toPartialEntity())
                    }
                }
            }
        }.asFlow()

    override fun getFavorites(): Flow<List<GenresModel>> {
        return localDataSource.getFavorite().map { list ->
            list.map {
                it.toModel()
            }
        }
    }

    override fun setFavorite(genresModel: GenresModel, state: Boolean) {
        val genreEntity = genresModel.toEntity()
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.setFavorite(genreEntity, state)
        }
    }
}