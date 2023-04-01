package com.dicoding.gamescapstone.detail_game

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dicoding.core.domain.model.game.GameModel
import com.dicoding.core.utils.DataHelper
import com.dicoding.gamescapstone.R
import com.dicoding.gamescapstone.databinding.ActivityDetailGameBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("DEPRECATION")
class DetailGameActivity : AppCompatActivity() {

    private val detailGameViewModel: DetailGameViewModel by viewModel()

    private lateinit var binding: ActivityDetailGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val detailGame = intent.getParcelableExtra<GameModel>(DataHelper.EXTRA_DATA)
        showDetailGame(detailGame)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun showDetailGame(gameModel: GameModel?){
        gameModel?.let {
            supportActionBar?.title = gameModel.name
            Glide.with(this@DetailGameActivity)
                .load(gameModel.backgroundImage)
                .into(binding.ivDetailImage)
            val rating = "${gameModel.rating} (${gameModel.ratingsCount})"
            binding.content.tvRating.text = rating
            binding.content.tvGenres.text = DataHelper.genreListing(gameModel.genres)
            binding.content.tvReleaseDate.text = DataHelper.formatDate(gameModel.released)
            var statusFavorite = gameModel.isFavorite
            setStatusFavorite(statusFavorite)
            binding.fab.setOnClickListener {
                statusFavorite = !statusFavorite
                detailGameViewModel.setFavoriteGame(gameModel, statusFavorite)
                val message = if (statusFavorite) getString(R.string.add_favorite) else getString(R.string.remove_favorite)
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                setStatusFavorite(statusFavorite)
            }
            detailGameViewModel.currentSS.observe(this){ ss ->
                ss?.let { index ->
                    binding.content.progressBar.visibility = View.VISIBLE
                    Glide.with(this)
                        .load(gameModel.shortScreenshots[index].image)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                binding.content.progressBar.visibility = View.GONE
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                binding.content.progressBar.visibility = View.GONE
                                return false
                            }

                        })
                        .into(binding.content.imgSs)
                    if (index == 0){
                        binding.content.imgPrev.visibility = View.GONE
                    } else {
                        binding.content.imgPrev.visibility = View.VISIBLE
                    }
                    if (index == gameModel.shortScreenshots.size - 1){
                        binding.content.imgNext.visibility = View.GONE
                    } else {
                        binding.content.imgNext.visibility = View.VISIBLE
                    }
                    binding.content.imgPrev.setOnClickListener {
                        val newInt = index - 1
                        detailGameViewModel.updateCurrentSS(newInt)
                    }
                    binding.content.imgNext.setOnClickListener {
                        val newInt = index + 1
                        detailGameViewModel.updateCurrentSS(newInt)
                    }
                }
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_fill))
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border))
        }
    }
}