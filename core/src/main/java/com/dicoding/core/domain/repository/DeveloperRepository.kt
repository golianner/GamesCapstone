package com.dicoding.core.domain.repository

import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.developer.DeveloperModel
import kotlinx.coroutines.flow.Flow

interface DeveloperRepository {
    fun getAll(refresh: Boolean): Flow<Resource<List<DeveloperModel>>>
    fun getFavorites(): Flow<List<DeveloperModel>>
    fun setFavorite(developerModel: DeveloperModel, state: Boolean)
}