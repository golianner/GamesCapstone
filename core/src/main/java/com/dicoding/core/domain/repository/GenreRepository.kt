package com.dicoding.core.domain.repository

import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.genre.GenresModel
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    fun getAll(refresh: Boolean): Flow<Resource<List<GenresModel>>>
    fun getFavorites(): Flow<List<GenresModel>>
    fun setFavorite(genresModel: GenresModel, state: Boolean)
}