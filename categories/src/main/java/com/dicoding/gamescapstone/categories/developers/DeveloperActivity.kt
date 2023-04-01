package com.dicoding.gamescapstone.categories.developers

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
import com.dicoding.core.utils.ui.DeveloperAdaper
import com.dicoding.gamescapstone.categories.R
import com.dicoding.gamescapstone.categories.databinding.ActivityDeveloperBinding
import com.dicoding.gamescapstone.di.categoriesDeveloperModule
import com.dicoding.gamescapstone.find_games.FindGamesActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class DeveloperActivity : AppCompatActivity() {

    private val viewModel: DeveloperViewModel by viewModel()

    private lateinit var binding: ActivityDeveloperBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(categoriesDeveloperModule)
        binding = ActivityDeveloperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.categories_developers)

        val developerAdapter = DeveloperAdaper()
        developerAdapter.onFavoriteClick = { selectedData ->
            val isFavorite = !selectedData.isFavorite
            viewModel.setFavorite(selectedData, isFavorite)
            val message = if (isFavorite) getString(com.dicoding.gamescapstone.R.string.add_favorite_developer)
            else getString(com.dicoding.gamescapstone.R.string.remove_favorite_developer)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        developerAdapter.onFindClick = { selectedData ->
            val intent = Intent(this@DeveloperActivity, FindGamesActivity::class.java)
            intent.putExtra(DataHelper.EXTRA_DATA, FilterType.Developer(selectedData.slug))
            startActivity(intent)
        }

        viewModel.data.observe(this){ developerModel ->
            if (developerModel != null){
                when(developerModel){
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text = developerModel.message
                            ?: getString(com.dicoding.gamescapstone.R.string.something_wrong)
                    }
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.rvDevelopers.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        developerAdapter.setData(developerModel.data)
                    }
                    else -> {}
                }
            }
        }

        with(binding.rvDevelopers) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = developerAdapter
        }

        with(binding.swipeLayout){
            this.setOnRefreshListener {
                viewModel.viewModelScope.launch {
                    delay(1000)
                }
                isRefreshing = false
                binding.rvDevelopers.visibility = View.INVISIBLE
                viewModel.refresh()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}