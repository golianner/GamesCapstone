package com.dicoding.core.domain.usecase.genre

import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.genre.GenresModel
import kotlinx.coroutines.flow.Flow

interface GenreUseCase {
    fun getAll(refresh: Boolean = false): Flow<Resource<List<GenresModel>>>
    fun getFavorites(): Flow<List<GenresModel>>
    fun setFavorite(genresModel: GenresModel, state: Boolean)
}