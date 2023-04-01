package com.dicoding.gamescapstone.categories.developers

import androidx.lifecycle.*
import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.developer.DeveloperModel
import com.dicoding.core.domain.usecase.developer.DeveloperUseCase
import kotlinx.coroutines.launch

class DeveloperViewModel(private val useCase: DeveloperUseCase): ViewModel() {
    private val _data = MutableLiveData<Resource<List<DeveloperModel>>>()
    var data : LiveData<Resource<List<DeveloperModel>>> = _data

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

    fun setFavorite(model: DeveloperModel, newStatus: Boolean) =
        useCase.setFavorite(model, newStatus)
}