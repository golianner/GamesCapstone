package com.dicoding.core.domain.repository

import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.axa_test.AxaTestModel
import kotlinx.coroutines.flow.Flow

interface AxaRepository {
    fun getPosts(): Flow<Resource<List<AxaTestModel>>>
}