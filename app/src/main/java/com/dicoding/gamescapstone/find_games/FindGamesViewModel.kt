package com.dicoding.gamescapstone.find_games

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.game.GameModel
import com.dicoding.core.domain.usecase.game.GameUseCase
import com.dicoding.core.utils.FilterType
import kotlinx.coroutines.launch

class FindGamesViewModel (private val useCase: GameUseCase): ViewModel() {
    private val _games = MutableLiveData<Resource<List<GameModel>>>()
    var games : LiveData<Resource<List<GameModel>>> = _games

    fun getData(filterType: FilterType) {
        viewModelScope.launch {
            useCase.getAllFiltered(filterType).collect {
                _games.value = it
            }
        }
    }
}