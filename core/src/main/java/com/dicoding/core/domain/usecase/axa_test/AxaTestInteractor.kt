package com.dicoding.core.domain.usecase.axa_test

import com.dicoding.core.domain.repository.AxaRepository

class AxaTestInteractor (private val repository: AxaRepository): AxaTestUseCase {
    override fun getPosts() = repository.getPosts()
}