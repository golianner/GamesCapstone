package com.dicoding.gamescapstone.favorites

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.core.utils.UriConst
import com.dicoding.core.utils.splithelper.SplitHelper
import com.dicoding.gamescapstone.R
import com.dicoding.gamescapstone.databinding.FragmentFavoritesBinding

class FavoritesFragment: Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvGames.setOnClickListener {
            if (SplitHelper.installModule(requireContext(), getString(R.string.favorite_module))){
                val uri = Uri.parse(UriConst.favoriteGames)
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
        binding.tvDevelopers.setOnClickListener {
            if (SplitHelper.installModule(requireContext(), getString(R.string.favorite_module))){
                val uri = Uri.parse(UriConst.favoriteDevelopers)
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
        binding.tvGenres.setOnClickListener {
            if (SplitHelper.installModule(requireContext(), getString(R.string.favorite_module))){
                val uri = Uri.parse(UriConst.favoriteGenres)
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
    }
}