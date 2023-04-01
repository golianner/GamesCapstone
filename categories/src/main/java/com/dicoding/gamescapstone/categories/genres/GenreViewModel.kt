package com.dicoding.gamescapstone.categories.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.genre.GenresModel
import com.dicoding.core.domain.usecase.genre.GenreUseCase
import kotlinx.coroutines.launch

class GenreViewModel(private val useCase: GenreUseCase): ViewModel() {
    private val _data = MutableLiveData<Resource<List<GenresModel>>>()
    var data : LiveData<Resource<List<GenresModel>>> = _data

    init {
        getAllData()
    }

    private fun getAllData(refresh: Boolean = false) {
        viewModelScope.launch {
            useCase.getAll(refresh).collect {
                _data.value = it
            }
        }
    }

    fun refresh() {
        getAllData(true)
    }

    fun setFavorite(model: GenresModel, newStatus: Boolean) =
        useCase.setFavorite(model, newStatus)
}