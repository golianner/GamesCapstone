package com.dicoding.core.domain.usecase.axa_test

import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.axa_test.AxaTestModel
import kotlinx.coroutines.flow.Flow

interface AxaTestUseCase {
    fun getPosts(): Flow<Resource<List<AxaTestModel>>>
}