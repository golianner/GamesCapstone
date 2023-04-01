package com.dicoding.gamescapstone.di

import com.dicoding.gamescapstone.favorite.developers.FavoriteDevelopersViewModel
import com.dicoding.gamescapstone.favorite.games.FavoriteGamesViewModel
import com.dicoding.gamescapstone.favorite.genres.FavoriteGenresViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteGameModule = module {
    viewModel { FavoriteGamesViewModel(get()) }
}
val favoriteDeveloperModule = module {
    viewModel { FavoriteDevelopersViewModel(get()) }
}
val favoriteGenreModule = module {
    viewModel { FavoriteGenresViewModel(get()) }
}