package com.dicoding.gamescapstone.favorite.games

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.core.utils.DataHelper
import com.dicoding.core.utils.ui.GameAdapter
import com.dicoding.gamescapstone.detail_game.DetailGameActivity
import com.dicoding.gamescapstone.di.favoriteGameModule
import com.dicoding.gamescapstone.favorite.R
import com.dicoding.gamescapstone.favorite.databinding.ActivityFavoriteGamesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteGamesActivity: AppCompatActivity() {

    private val favoriteGamesViewModel: FavoriteGamesViewModel by viewModel()

    private lateinit var binding: ActivityFavoriteGamesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(favoriteGameModule)
        binding = ActivityFavoriteGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.favorite_games)

        val gameAdapter = GameAdapter()
        gameAdapter.onItemClick = { selectedData ->
            val intent = Intent(this, DetailGameActivity::class.java)
            intent.putExtra(DataHelper.EXTRA_DATA, selectedData)
            startActivity(intent)
        }

        favoriteGamesViewModel.favorites.observe(this){ dataGame ->
            gameAdapter.setData(dataGame)
            binding.viewEmpty.root.visibility = if (dataGame.isNotEmpty()) View.GONE else View.VISIBLE
        }

        with(binding.rvGames) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = gameAdapter
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}