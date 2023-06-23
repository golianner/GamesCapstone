package com.dicoding.gamescapstone.axa_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dicoding.core.data.Resource
import com.dicoding.core.utils.DataHelper
import com.dicoding.core.utils.ui.PostAdapter
import com.dicoding.gamescapstone.R
import com.dicoding.gamescapstone.databinding.ActivityPostBinding
import com.dicoding.gamescapstone.detail_post.DetailPostActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostActivity : AppCompatActivity() {

    private val postViewModel: PostViewModel by viewModel()

    private lateinit var binding: ActivityPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostAdapter()
        adapter.onItemClick = { selectedData ->
            val intent = Intent(this, DetailPostActivity::class.java)
            intent.putExtra(DataHelper.EXTRA_DATA, selectedData)
            startActivity(intent)
        }

        postViewModel.posts.observe(this){ model ->
            if (model != null){
                when(model){
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        println("error")
                    }
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.rvPost.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        adapter.setData(model.data)
                    }
                    else -> {}
                }
            }
        }
    }
}