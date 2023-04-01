package com.dicoding.core.domain.usecase.game

import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.game.GameModel
import com.dicoding.core.utils.FilterType
import kotlinx.coroutines.flow.Flow

interface GameUseCase {
    fun getAll(refresh: Boolean = false): Flow<Resource<List<GameModel>>>
    fun getFavorites(): Flow<List<GameModel>>
    suspend fun searchAutoComplete(search: String): List<GameModel>
    fun setFavorite(gameModel: GameModel, state: Boolean)
    fun getAllFiltered(filterType: FilterType): Flow<Resource<List<GameModel>>>
}