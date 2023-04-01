package com.dicoding.gamescapstone.favorite.developers

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.core.utils.DataHelper
import com.dicoding.core.utils.FilterType
import com.dicoding.core.utils.ui.DeveloperAdaper
import com.dicoding.gamescapstone.di.favoriteDeveloperModule
import com.dicoding.gamescapstone.favorite.R
import com.dicoding.gamescapstone.favorite.databinding.ActivityFavoriteDeveloperBinding
import com.dicoding.gamescapstone.find_games.FindGamesActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteDevelopersActivity : AppCompatActivity() {

    private val viewModel: FavoriteDevelopersViewModel by viewModel()

    private lateinit var binding: ActivityFavoriteDeveloperBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(favoriteDeveloperModule)
        binding = ActivityFavoriteDeveloperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.favorite_developers)

        val developerAdapter = DeveloperAdaper()
        developerAdapter.onFavoriteClick = { selectedData ->
            val isFavorite = !selectedData.isFavorite
            viewModel.setFavorite(selectedData, isFavorite)
            val message = if (isFavorite) getString(com.dicoding.gamescapstone.R.string.add_favorite_developer)
            else getString(com.dicoding.gamescapstone.R.string.remove_favorite_developer)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        developerAdapter.onFindClick = { selectedData ->
            val intent = Intent(this@FavoriteDevelopersActivity, FindGamesActivity::class.java)
            intent.putExtra(DataHelper.EXTRA_DATA, FilterType.Developer(selectedData.slug))
            startActivity(intent)
        }

        viewModel.favorites.observe(this){ dataDeveloper ->
            developerAdapter.setData(dataDeveloper)
            binding.viewEmpty.root.visibility = if (dataDeveloper.isNotEmpty()) View.GONE else View.VISIBLE
        }

        with(binding.rvDevelopers) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = developerAdapter
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}