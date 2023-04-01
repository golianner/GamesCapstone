package com.dicoding.core.domain.repository

import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.game.GameModel
import com.dicoding.core.utils.FilterType
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getAll(refresh: Boolean): Flow<Resource<List<GameModel>>>
    fun getFavorites(): Flow<List<GameModel>>
    suspend fun searchAutoComplete(search: String): List<GameModel>
    fun setFavorite(model: GameModel, state: Boolean)
    fun getAllFiltered(filterType: FilterType): Flow<Resource<List<GameModel>>>
}