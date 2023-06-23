package com.dicoding.gamescapstone.axa_test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.core.domain.usecase.axa_test.AxaTestUseCase
import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.axa_test.AxaTestModel
import kotlinx.coroutines.launch

class PostViewModel (private val useCase: AxaTestUseCase): ViewModel() {
    private val _posts = MutableLiveData<Resource<List<AxaTestModel>>>()
    var posts : LiveData<Resource<List<AxaTestModel>>> = _posts

    init {
        viewModelScope.launch {
            useCase.getPosts().collect {
                _posts.value = it
            }
        }
    }
}