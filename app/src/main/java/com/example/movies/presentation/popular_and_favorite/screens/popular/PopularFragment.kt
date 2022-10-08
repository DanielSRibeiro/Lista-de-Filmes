package com.example.movies.presentation.popular_and_favorite.screens.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.movies.databinding.FragmentPopularBinding
import com.example.movies.presentation.details.MovieArgs
import com.example.movies.presentation.popular_and_favorite.PopularAndFavoriteFragmentDirections
import com.example.movies.presentation.popular_and_favorite.screens.popular.adapter.PopularAdapter
import com.example.movies.util.IOnAction
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularFragment : Fragment(), IOnAction {

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    private val popularViewModel: PopularViewModel by viewModel()
    private lateinit var popularAdapter: PopularAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setObservers()
        setupAdapter()
        popularViewModel.getAllMovies()
    }

    override fun executeAction(name: String) {
        popularViewModel.seachMovie(name)
    }

    override fun executeAction() {
        popularViewModel.getAllMovies()
    }

    private fun setListeners() {
        binding.progressBarPopular.visibility = View.VISIBLE
        binding.refreshPopular.setOnRefreshListener {
            popularViewModel.getAllMovies()
            binding.refreshPopular.isRefreshing = false
        }
    }


    private fun setObservers() {
        popularViewModel.list.observe(viewLifecycleOwner) {
            it?.let { list ->
                popularAdapter.submitList(list)
            }
            binding.progressBarPopular.visibility = View.GONE
        }
    }

    private fun setupAdapter() {
        binding.recyclerViewPopular.apply {
            popularAdapter = PopularAdapter { movie ->
                val action =
                    PopularAndFavoriteFragmentDirections.actionViewPageFragmentToDetailsFragment(
                        MovieArgs(
                            id = movie.id,
                            title = movie.title,
                            posterPath = movie.posterPath,
                            overview = movie.overview,
                            note = movie.note,
                            releaseData = movie.releaseData,
                            genreIds = movie.genreIds,
                            backdropPath = movie.backdropPath,
                            posterUrl = movie.posterPath,
                            backdropUrl = movie.backdropUrl
                        )
                    )
                findNavController().navigate(action)
            }

            adapter = popularAdapter
        }
    }
}