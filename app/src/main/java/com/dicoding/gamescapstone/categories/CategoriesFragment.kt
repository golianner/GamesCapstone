package com.dicoding.gamescapstone.categories

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
import com.dicoding.gamescapstone.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDevelopers.setOnClickListener {
            if (SplitHelper.installModule(requireContext(), getString(R.string.categories_module))){
                val uri = Uri.parse(UriConst.categoriesDevelopers)
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
        binding.tvGenres.setOnClickListener {
            if (SplitHelper.installModule(requireContext(), getString(R.string.categories_module))){
                val uri = Uri.parse(UriConst.categoriesGenres)
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
    }
}