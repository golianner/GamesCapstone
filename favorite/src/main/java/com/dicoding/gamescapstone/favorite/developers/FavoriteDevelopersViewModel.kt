package com.dicoding.gamescapstone.favorite.developers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.core.domain.model.developer.DeveloperModel
import com.dicoding.core.domain.usecase.developer.DeveloperUseCase

class FavoriteDevelopersViewModel(private val useCase: DeveloperUseCase): ViewModel() {
    val favorites = useCase.getFavorites().asLiveData()

    fun setFavorite(model: DeveloperModel, newStatus: Boolean) =
        useCase.setFavorite(model, newStatus)
}