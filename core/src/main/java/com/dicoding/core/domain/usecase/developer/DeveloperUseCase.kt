package com.dicoding.core.domain.usecase.developer

import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.developer.DeveloperModel
import kotlinx.coroutines.flow.Flow

interface DeveloperUseCase {
    fun getAll(refresh: Boolean = false): Flow<Resource<List<DeveloperModel>>>
    fun getFavorites(): Flow<List<DeveloperModel>>
    fun setFavorite(developerModel: DeveloperModel, state: Boolean)
}