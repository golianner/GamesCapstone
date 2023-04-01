package com.dicoding.core.data.repository

import com.dicoding.core.data.NetworkBoundResource
import com.dicoding.core.data.Resource
import com.dicoding.core.data.source.local.data_source.LocalGameSource
import com.dicoding.core.data.source.remote.data_source.RemoteGameSource
import com.dicoding.core.data.source.remote.network.ApiResponse
import com.dicoding.core.data.source.remote.response.game.GameResponse
import com.dicoding.core.domain.model.game.GameModel
import com.dicoding.core.domain.repository.GameRepository
import com.dicoding.core.utils.FilterType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GameRepositoryImpl(
    private val remoteDataSource: RemoteGameSource,
    private val localDataSource: LocalGameSource
): GameRepository {
    override fun getAll(refresh: Boolean): Flow<Resource<List<GameModel>>> =
        object : NetworkBoundResource<List<GameModel>, List<GameResponse>>() {
            override fun loadFromDB(): Flow<List<GameModel>> {
                return localDataSource.getAll().map { list ->
                    list.map {
                        it.toModel()
                    }
                }
            }

            override fun shouldFetch(data: List<GameModel>?): Boolean {
                var fetch = data == null || data.isEmpty()
                if (refresh){
                    fetch = true
                }
                return fetch
            }

            override suspend fun createCall(): Flow<ApiResponse<List<GameResponse>>> =
                remoteDataSource.getAll()

            override suspend fun saveCallResult(data: List<GameResponse>) {
                data.map {
                    val entity = it.toEntity()
                    val rowId = localDataSource.insert(entity)
                    if (rowId == -1L){
                        localDataSource.update(it.toPartialEntity())
                    }
                }
            }
        }.asFlow()

    override fun getFavorites(): Flow<List<GameModel>> {
        return localDataSource.getFavorite().map { list ->
            list.map {
                it.toModel()
            }
        }
    }

    override suspend fun searchAutoComplete(search: String): List<GameModel> {
        var list = emptyList<GameModel>()
        remoteDataSource.searchAutoComplete(search).collect { res ->
            when(res) {
                is ApiResponse.Success -> {
                    val data = res.data.let { data ->
                        data.map {
                            it.toDomainModel()
                        }
                    }
                    list = data
                }
                else -> {}
            }
        }
        return list
    }

    override fun setFavorite(model: GameModel, state: Boolean) {
        val gameEntity = model.toEntity()
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.setFavorite(gameEntity, state)
        }
    }

    override fun getAllFiltered(filterType: FilterType): Flow<Resource<List<GameModel>>> =
        object : NetworkBoundResource<List<GameModel>, List<GameResponse>>() {
            override fun loadFromDB(): Flow<List<GameModel>> {
                return localDataSource.getAll().map { list ->
                    list.map {
                        it.toModel()
                    }
                }
            }

            override fun shouldFetch(data: List<GameModel>?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<List<GameResponse>>> =
                remoteDataSource.getAllFiltered(filterType)

            override suspend fun saveCallResult(data: List<GameResponse>) {
                data.map {
                    val entity = it.toEntity()
                    val rowId = localDataSource.insert(entity)
                    if (rowId == -1L){
                        localDataSource.update(it.toPartialEntity())
                    }
                }
            }
        }.asFlow()
}