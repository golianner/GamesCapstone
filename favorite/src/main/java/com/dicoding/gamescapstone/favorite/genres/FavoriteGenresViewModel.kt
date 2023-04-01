package com.dicoding.gamescapstone.favorite.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.core.domain.model.genre.GenresModel
import com.dicoding.core.domain.usecase.genre.GenreUseCase

class FavoriteGenresViewModel(private val useCase: GenreUseCase): ViewModel() {
    val favorites = useCase.getFavorites().asLiveData()

    fun setFavorite(model: GenresModel, newStatus: Boolean) =
        useCase.setFavorite(model, newStatus)
}