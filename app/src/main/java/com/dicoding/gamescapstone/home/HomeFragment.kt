package com.dicoding.gamescapstone.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.core.data.Resource
import com.dicoding.core.utils.DataHelper
import com.dicoding.core.utils.ui.GameAdapter
import com.dicoding.gamescapstone.databinding.FragmentHomeBinding
import com.dicoding.gamescapstone.detail_game.DetailGameActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@kotlinx.coroutines.FlowPreview
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null){
            val gameAdapter = GameAdapter()
            gameAdapter.onItemClick = { selectedData ->
                println(selectedData.name)
                val intent = Intent(activity, DetailGameActivity::class.java)
                intent.putExtra(DataHelper.EXTRA_DATA, selectedData)
                startActivity(intent)
            }

            homeViewModel.games.observe(viewLifecycleOwner){ gameModel ->
                if (gameModel != null){
                    when(gameModel){
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.viewError.root.visibility = View.VISIBLE
                            binding.viewError.tvError.text = gameModel.message
                                ?: getString(com.dicoding.core.R.string.something_wrong)
                        }
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding.rvGames.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                            gameAdapter.setData(gameModel.data)
                        }
                        else -> {}
                    }
                }
            }

            with(binding.rvGames) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = gameAdapter
            }

            with(binding.swipeLayout){
                this.setOnRefreshListener {
                    homeViewModel.viewModelScope.launch {
                        delay(1000)
                    }
                    isRefreshing = false
                    binding.rvGames.visibility = View.INVISIBLE
                    homeViewModel.refreshGames()
                }
            }

            binding.edtSearchGames.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    lifecycleScope.launch {
                        homeViewModel.searchText.value = p0.toString()
                    }
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
            binding.edtSearchGames.threshold = 1
            homeViewModel.searchResult.observe(viewLifecycleOwner) { listGame ->
                val gameNames = listGame.map { it.name }
                val adapter = ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, gameNames)
                adapter.notifyDataSetChanged()
                binding.edtSearchGames.setAdapter(adapter)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}