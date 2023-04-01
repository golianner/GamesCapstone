package com.dicoding.gamescapstone.di

import com.dicoding.core.domain.usecase.developer.DeveloperInteractor
import com.dicoding.core.domain.usecase.developer.DeveloperUseCase
import com.dicoding.core.domain.usecase.game.GameInteractor
import com.dicoding.core.domain.usecase.game.GameUseCase
import com.dicoding.core.domain.usecase.genre.GenreInteractor
import com.dicoding.core.domain.usecase.genre.GenreUseCase
import com.dicoding.gamescapstone.detail_game.DetailGameViewModel
import com.dicoding.gamescapstone.home.HomeViewModel
import com.dicoding.gamescapstone.find_games.FindGamesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@kotlinx.coroutines.ExperimentalCoroutinesApi
@kotlinx.coroutines.FlowPreview

val listAppModule = listOf(
    // Use Case Module
    module {
        factory<GameUseCase> { GameInteractor(get()) }
        factory<DeveloperUseCase> { DeveloperInteractor(get()) }
        factory<GenreUseCase> { GenreInteractor(get()) }
    },
    // View Model Module
    module {
        viewModel { HomeViewModel(get()) }
        viewModel { DetailGameViewModel(get()) }
        viewModel { FindGamesViewModel(get()) }
    }
)