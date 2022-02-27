package com.example.filmes.presentation.fragment.viewpage.fragment.popular

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmes.R
import com.example.filmes.databinding.FragmentPopularBinding
import com.example.filmes.domain.model.MovieDto
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
            getPopularMovies()
            getErro()
            refreshPopular.setOnRefreshListener {
                movieViewModel.input.getAllMovies(null)
                refreshPopular.isRefreshing = false
            }
        }
    }

    fun getPopularMovies() {
        movieViewModel.input.getAllMovies(null)
        movieViewModel.output.movieList.observe(requireActivity()) { listaFilmes ->
            movieList = listaFilmes
            updateAdapter(movieList)
            binding.progressBarPopular.visibility = View.GONE
        }
    }

    private fun getErro() {
        movieViewModel.output.error.observe(requireActivity()) { erro ->
            showToast("Erro de conexão")
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