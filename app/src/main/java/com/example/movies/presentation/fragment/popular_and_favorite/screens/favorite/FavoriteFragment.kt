package com.example.movies.presentation.fragment.popular_and_favorite.screens.favorite

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.movies.data.database.entity.MovieEntity
import com.example.movies.databinding.FragmentFavoriteBinding
import com.example.movies.domain.model.Movie
import com.example.movies.presentation.fragment.LocalViewModel
import com.example.movies.presentation.fragment.popular_and_favorite.PopularAndFavoriteFragmentDirections
import com.example.movies.utilis.IOnAction
import com.example.movies.utilis.JsonService
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class FavoriteFragment : Fragment(), IOnAction {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val localViewModel: LocalViewModel by viewModel()

    private lateinit var listMovieSalvo: List<Movie>

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

        getListaFilmes()

        binding.apply {
            refreshFavorite.setOnRefreshListener {
                localViewModel.getSeachMovie(null)
                refreshFavorite.isRefreshing = false
            }
        }
    }

    private fun getListaFilmes() {
        localViewModel.getSeachMovie(null)
        localViewModel.savedList.observe(requireActivity()) { listaEntity ->
            listMovieSalvo = listaEntity
            setupAdapter(listMovieSalvo)

            binding.txtNoneFavorite.visibility =
                if (listaEntity.isNotEmpty()) View.GONE
                else View.VISIBLE
        }
    }

    private fun setupAdapter(listMovieSalvo: List<Movie>) {
        binding.recyclerViewFavorite.apply {
            favoriteAdapter = FavoriteAdapter(listMovieSalvo)
            favoriteAdapter.setOnClick { onClick(it) }
            favoriteAdapter.setOnClickButton { onClickButton(it) }
            adapter = favoriteAdapter
        }
    }

    private fun onClick(position: Int) {
        val movie = listMovieSalvo[position]

        val action = PopularAndFavoriteFragmentDirections.actionViewPageFragmentToDetailsFragment(
            movie, movie.releaseData
        )
        findNavController().navigate(action)
    }

    private fun onClickButton(movie: Movie) {
        localViewModel.deleteMovie(movie.id.toInt())
        localViewModel.getSeachMovie(null)
    }

    override fun executeAction(name: String?) {
        if (name.isNullOrBlank())
            localViewModel.getSeachMovie(name)
        else
            localViewModel.getSeachMovie(null)
        binding.txtNoneFavorite.text = "Filme não encontrado"
    }
}