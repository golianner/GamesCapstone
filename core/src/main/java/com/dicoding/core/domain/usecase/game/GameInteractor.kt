package com.dicoding.core.domain.usecase.game

import com.dicoding.core.domain.model.game.GameModel
import com.dicoding.core.domain.repository.GameRepository
import com.dicoding.core.utils.FilterType

class GameInteractor(
    private val repository: GameRepository
): GameUseCase {
    override fun getAll(refresh: Boolean) = repository.getAll(refresh)

    override fun getFavorites() = repository.getFavorites()

    override suspend fun searchAutoComplete(search: String) = repository.searchAutoComplete(search)

    override fun setFavorite(gameModel: GameModel, state: Boolean) =
        repository.setFavorite(gameModel, state)

    override fun getAllFiltered(filterType: FilterType) = repository.getAllFiltered(filterType)
}