package com.example.movies.presentation.popular_and_favorite.screens.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.movies.databinding.FragmentPopularBinding
import com.example.movies.presentation.popular_and_favorite.PopularAndFavoriteFragmentDirections
import com.example.movies.presentation.popular_and_favorite.screens.popular.adapter.PopularAdapter
import com.example.movies.util.IOnAction
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularFragment : Fragment(), IOnAction {

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    private val movieViewModel: MovieViewModel by viewModel()
    private lateinit var popularAdapter: PopularAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        binding.progressBarPopular.visibility = View.VISIBLE
        movieViewModel.getAllMovies(null)
        initMovieObserve()
        binding.refreshPopular.setOnRefreshListener {
            movieViewModel.getAllMovies(null)
            binding.refreshPopular.isRefreshing = false
        }
    }


    private fun initMovieObserve() {
        movieViewModel.list.observe(viewLifecycleOwner) {
            it?.let { result ->
                popularAdapter.submitList(result)
            }
            binding.progressBarPopular.visibility = View.GONE
        }
    }

    private fun setupAdapter() {
        binding.recyclerViewPopular.apply {
            popularAdapter = PopularAdapter { movie ->
                val action =
                    PopularAndFavoriteFragmentDirections.actionViewPageFragmentToDetailsFragment(
                        movie
                    )
                findNavController().navigate(action)
            }

            adapter = popularAdapter
        }
    }

    override fun executeAction(query: String?) {
//        movieViewModel.setQuery(query)
    }
}