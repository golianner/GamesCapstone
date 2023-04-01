package com.dicoding.gamescapstone.favorite.genres

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.core.utils.DataHelper
import com.dicoding.core.utils.FilterType
import com.dicoding.core.utils.ui.GenreAdapter
import com.dicoding.gamescapstone.di.favoriteGenreModule
import com.dicoding.gamescapstone.favorite.R
import com.dicoding.gamescapstone.favorite.databinding.ActivityFavoriteGenresBinding
import com.dicoding.gamescapstone.find_games.FindGamesActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteGenresActivity : AppCompatActivity() {

    private val viewModel: FavoriteGenresViewModel by viewModel()

    private lateinit var binding: ActivityFavoriteGenresBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(favoriteGenreModule)
        binding = ActivityFavoriteGenresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.favorite_genres)

        val genreAdapter = GenreAdapter()
        genreAdapter.onFavoriteClick = { selectedData ->
            val isFavorite = !selectedData.isFavorite
            viewModel.setFavorite(selectedData, isFavorite)
            val message = if (isFavorite) getString(com.dicoding.gamescapstone.R.string.add_favorite_genre)
            else getString(com.dicoding.gamescapstone.R.string.remove_favorite_genre)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        genreAdapter.onFindClick = { selectedData ->
            val intent = Intent(this@FavoriteGenresActivity, FindGamesActivity::class.java)
            intent.putExtra(DataHelper.EXTRA_DATA, FilterType.Genre(selectedData.slug))
            startActivity(intent)
        }

        viewModel.favorites.observe(this){ dataGenre ->
            genreAdapter.setData(dataGenre)
            binding.viewEmpty.root.visibility = if (dataGenre.isNotEmpty()) View.GONE else View.VISIBLE
        }

        with(binding.rvGenres) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = genreAdapter
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}