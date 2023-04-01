package com.dicoding.gamescapstone.favorite.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.core.domain.usecase.game.GameUseCase

class FavoriteGamesViewModel(useCase: GameUseCase): ViewModel() {
    val favorites = useCase.getFavorites().asLiveData()
}