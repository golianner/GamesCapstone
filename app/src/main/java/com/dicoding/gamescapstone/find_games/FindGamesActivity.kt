package com.dicoding.gamescapstone.find_games

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.core.data.Resource
import com.dicoding.core.utils.DataHelper
import com.dicoding.core.utils.FilterType
import com.dicoding.core.utils.ui.GameAdapter
import com.dicoding.gamescapstone.databinding.ActivityFindGamesBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("DEPRECATION")
class FindGamesActivity : AppCompatActivity() {

    private val viewModel: FindGamesViewModel by viewModel()

    private lateinit var binding: ActivityFindGamesBinding

    private val gameAdapter = GameAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val filterType = intent.getParcelableExtra<FilterType>(DataHelper.EXTRA_DATA)
        loadFilterGames(filterType)

        viewModel.games.observe(this){ gameModel ->
            if (gameModel != null){
                when(gameModel){
                    is Resource.Error -> {
                        binding.contentFindGames.progressBar.visibility = View.GONE
                        binding.contentFindGames.viewError.root.visibility = View.VISIBLE
                        binding.contentFindGames.viewError.tvError.text = gameModel.message
                            ?: getString(com.dicoding.gamescapstone.R.string.something_wrong)
                    }
                    is Resource.Loading -> {
                        binding.contentFindGames.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.contentFindGames.rvGames.visibility = View.VISIBLE
                        binding.contentFindGames.progressBar.visibility = View.GONE
                        gameAdapter.setData(gameModel.data)
                    }
                }
            }
        }
    }

    private fun loadFilterGames(filterType: FilterType?) {
        filterType?.let {
            val title = buildString {
                append("Filter Game by ")
                append(filterType.type)
            }
            supportActionBar?.title = title
            binding.contentFindGames.filterIdentifier.text = filterType.identifier

            viewModel.getData(filterType)

            with(binding.contentFindGames.rvGames) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = gameAdapter
            }

            with(binding.contentFindGames.swipeLayout){
                this.setOnRefreshListener {
                    viewModel.viewModelScope.launch {
                        delay(1000)
                    }
                    isRefreshing = false
                    binding.contentFindGames.rvGames.visibility = View.INVISIBLE
                    viewModel.getData(filterType)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}