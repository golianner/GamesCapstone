package com.dicoding.gamescapstone.home

import androidx.lifecycle.*
import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.game.GameModel
import com.dicoding.core.domain.usecase.game.GameUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@kotlinx.coroutines.FlowPreview
class HomeViewModel (private val gameUseCase: GameUseCase): ViewModel() {
    private val _games = MutableLiveData<Resource<List<GameModel>>>()
    var games : LiveData<Resource<List<GameModel>>> = _games

    val searchText = MutableStateFlow("")

    init {
        getAllGames()
    }

    private fun getAllGames(refresh: Boolean = false) {
        viewModelScope.launch {
            gameUseCase.getAll(refresh).collect {
                _games.value = it
            }
        }
    }

    fun refreshGames() {
        getAllGames(true)
    }

    val searchResult = searchText
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
            it.trim().length > 2
        }
        .mapLatest {
            gameUseCase.searchAutoComplete(it)
        }
        .asLiveData()
}