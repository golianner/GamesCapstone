package com.dicoding.gamescapstone.categories.genres

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.core.data.Resource
import com.dicoding.core.utils.DataHelper
import com.dicoding.core.utils.FilterType
import com.dicoding.core.utils.ui.GenreAdapter
import com.dicoding.gamescapstone.categories.R
import com.dicoding.gamescapstone.categories.databinding.ActivityGenreBinding
import com.dicoding.gamescapstone.di.categoriesGenreModule
import com.dicoding.gamescapstone.find_games.FindGamesActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class GenreActivity : AppCompatActivity() {

    private val viewModel: GenreViewModel by viewModel()

    private lateinit var binding: ActivityGenreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(categoriesGenreModule)
        binding = ActivityGenreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.categories_genres)

        val genreAdapter = GenreAdapter()
        genreAdapter.onFavoriteClick = { selectedData ->
            val isFavorite = !selectedData.isFavorite
            viewModel.setFavorite(selectedData, isFavorite)
            val message = if (isFavorite) getString(com.dicoding.gamescapstone.R.string.add_favorite_developer)
            else getString(com.dicoding.gamescapstone.R.string.remove_favorite_developer)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        genreAdapter.onFindClick = { selectedData ->
            val intent = Intent(this@GenreActivity, FindGamesActivity::class.java)
            intent.putExtra(DataHelper.EXTRA_DATA, FilterType.Genre(selectedData.slug))
            startActivity(intent)
        }

        viewModel.data.observe(this){ genreModel ->
            if (genreModel != null){
                when(genreModel){
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text = genreModel.message
                            ?: getString(com.dicoding.gamescapstone.R.string.something_wrong)
                    }
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.rvGenres.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        genreAdapter.setData(genreModel.data)
                    }
                    else -> {}
                }
            }
        }

        with(binding.rvGenres) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = genreAdapter
        }

        with(binding.swipeLayout){
            this.setOnRefreshListener {
                viewModel.viewModelScope.launch {
                    delay(1000)
                }
                isRefreshing = false
                binding.rvGenres.visibility = View.INVISIBLE
                viewModel.refresh()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}