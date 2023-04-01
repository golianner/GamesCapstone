package com.dicoding.gamescapstone.detail_game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.core.domain.model.game.GameModel
import com.dicoding.core.domain.usecase.game.GameUseCase

class DetailGameViewModel(private val gameUseCase: GameUseCase): ViewModel() {
    fun setFavoriteGame(gameModel: GameModel, newStatus: Boolean) =
        gameUseCase.setFavorite(gameModel, newStatus)

    private val _currentSS = MutableLiveData(0)
    var currentSS: LiveData<Int> = _currentSS

    fun updateCurrentSS(i: Int){
        _currentSS.value = i
    }
}