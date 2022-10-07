package com.example.movies.presentation.popular_and_favorite.screens.favorite

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.movies.R
import com.example.movies.databinding.FragmentFavoriteBinding
import com.example.movies.domain.model.Movie
import com.example.movies.presentation.popular_and_favorite.PopularAndFavoriteFragmentDirections
import com.example.movies.util.IOnAction
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment(), IOnAction {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val favoriteViewModel: FavoriteViewModel by viewModel()
    lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
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
        favoriteViewModel.getSeachMovie(null)
    }

    override fun executeAction(name: String?) {
        if (name.isNullOrBlank())
            favoriteViewModel.getSeachMovie(name)
        else
            favoriteViewModel.getSeachMovie(null)

        binding.txtNoneFavorite.text = getString(R.string.label_movie_not_found)
    }

    private fun setListeners() {
        binding.refreshFavorite.setOnRefreshListener {
            favoriteViewModel.getSeachMovie(null)
            binding.refreshFavorite.isRefreshing = false
        }
    }

    private fun setObservers() {
        favoriteViewModel.savedList.observe(requireActivity()) {
            binding.txtNoneFavorite.visibility = View.VISIBLE

            it?.let { movieList ->
                binding.txtNoneFavorite.visibility = View.GONE
                favoriteAdapter.setOnClick { movie -> onClick(movie) }
                favoriteAdapter.setOnClickButton { movie -> onClickButton(movie) }
                favoriteAdapter.update(movieList)
            }
        }
    }

    private fun setupAdapter() {
        binding.recyclerViewFavorite.apply {
            favoriteAdapter = FavoriteAdapter(listOf())
            adapter = favoriteAdapter
        }
    }

    private fun onClick(movie: Movie) {
        val action = PopularAndFavoriteFragmentDirections.actionViewPageFragmentToDetailsFragment(
            movie
        )
        findNavController().navigate(action)
    }

    private fun onClickButton(movie: Movie) {
        favoriteViewModel.deleteMovie(movie.id)
        favoriteViewModel.getSeachMovie(null)
    }
}