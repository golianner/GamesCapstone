package com.dicoding.core.domain.usecase.genre

import com.dicoding.core.domain.model.genre.GenresModel
import com.dicoding.core.domain.repository.GenreRepository

class GenreInteractor(
    private val repository: GenreRepository
): GenreUseCase {
    override fun getAll(refresh: Boolean) = repository.getAll(refresh)

    override fun getFavorites() = repository.getFavorites()

    override fun setFavorite(genresModel: GenresModel, state: Boolean) =
        repository.setFavorite(genresModel, state)
}