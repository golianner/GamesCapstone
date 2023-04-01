package com.dicoding.gamescapstone.di

import com.dicoding.gamescapstone.categories.developers.DeveloperViewModel
import com.dicoding.gamescapstone.categories.genres.GenreViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoriesDeveloperModule = module {
    viewModel { DeveloperViewModel(get()) }
}

val categoriesGenreModule = module {
    viewModel { GenreViewModel(get()) }
}