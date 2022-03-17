package com.example.filmes.presentation.fragment.viewpage.fragment.popular

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmes.R
import com.example.filmes.databinding.FragmentPopularBinding
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.presentation.fragment.state.GetMoviesState
import com.example.filmes.presentation.fragment.viewpage.ViewPageFragmentDirections
import com.example.filmes.utilis.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularFragment : Fragment(R.layout.fragment_popular), OnItemClickPopularListener {

    private val movieViewModel: MovieViewModel by viewModel()
    lateinit var movieList: ArrayList<MovieDto>

    private lateinit var binding: FragmentPopularBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPopularBinding.bind(view)
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)

        val search = menu?.findItem(R.id.action_bar_search)
        val searchView = search?.actionView as SearchView
        searchView.queryHint = "Pesquisar Filmes"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean = false

            override fun onQueryTextChange(movieName: String?): Boolean {
                if (!movieName.isNullOrBlank())
                    movieViewModel.input.getAllMovies(movieName.toString())
                else
                    movieViewModel.input.getAllMovies(null)
                return true
            }
        })
    }

    private fun initView() {
        binding.apply {
            progressBarPopular.visibility = View.VISIBLE
            initMovieObserve()
            movieViewModel.input.getAllMovies(null)
            refreshPopular.setOnRefreshListener {
                movieViewModel.input.getAllMovies(null)
                refreshPopular.isRefreshing = false
            }
        }
    }

    fun initMovieObserve() {
        movieViewModel.output.getMoviesState.observe(requireActivity()) {
            when(it){
                is GetMoviesState.Success -> {
                    movieList = it.resultsMovies.movieList
                    updateAdapter(movieList)
                }
                is GetMoviesState.ErrorRequestNotFound -> showToast(it.message)
                is GetMoviesState.ErrorNetwork -> showToast(it.message)
                is GetMoviesState.ErrorNotSearch -> showToast(it.message)
            }
            binding.progressBarPopular.visibility = View.GONE
        }
    }

    fun updateAdapter(listMovies: ArrayList<MovieDto>) {
        var popularAdapter = PopularAdapter(this, listMovies)
        binding.recyclerViewPopular.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = popularAdapter
        }
    }

    override fun onClick(position: Int) {
        val action = ViewPageFragmentDirections.actionViewPageFragmentToDetailsFragment(
            movieList[position],
            null
        )
        findNavController().navigate(action)
    }
}