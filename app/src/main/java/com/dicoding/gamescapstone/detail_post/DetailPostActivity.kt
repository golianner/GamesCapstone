package com.dicoding.gamescapstone.detail_post

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dicoding.core.domain.model.axa_test.AxaTestModel
import com.dicoding.core.domain.model.game.GameModel
import com.dicoding.core.utils.DataHelper
import com.dicoding.gamescapstone.R
import com.dicoding.gamescapstone.databinding.ActivityDetailPostBinding
import com.dicoding.gamescapstone.databinding.ActivityPostBinding

class DetailPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_post)

        binding = ActivityDetailPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detail = intent.getParcelableExtra<AxaTestModel>(DataHelper.EXTRA_DATA)
        showDetail(detail)
    }

    private fun showDetail(gameModel: AxaTestModel?){
        gameModel?.let {
            binding.tvId.text = gameModel.id.toString()
            binding.tvUserid.text = gameModel.userId.toString()
            binding.tvTitle.text = gameModel.title.toString()
            binding.tvBody.text = gameModel.body.toString()
        }
    }
}