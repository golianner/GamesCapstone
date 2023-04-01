package com.dicoding.core.domain.usecase.developer

import com.dicoding.core.domain.model.developer.DeveloperModel
import com.dicoding.core.domain.repository.DeveloperRepository

class DeveloperInteractor(
    private val developerRepository: DeveloperRepository
): DeveloperUseCase {
    override fun getAll(refresh: Boolean) = developerRepository.getAll(refresh)

    override fun getFavorites() = developerRepository.getFavorites()

    override fun setFavorite(developerModel: DeveloperModel, state: Boolean) =
        developerRepository.setFavorite(developerModel, state)
}