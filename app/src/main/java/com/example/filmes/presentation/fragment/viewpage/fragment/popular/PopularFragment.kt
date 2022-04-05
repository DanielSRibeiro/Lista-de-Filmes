package com.example.filmes.presentation.fragment.viewpage.fragment.popular

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmes.presentation.fragment.viewpage.fragment.paging.MoviePagingAdapter
import com.example.filmes.R
import com.example.filmes.databinding.FragmentPopularBinding
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.presentation.fragment.state.GetMoviesState
import com.example.filmes.presentation.fragment.viewpage.ViewPageFragmentDirections
import com.example.filmes.utilis.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PopularFragment : Fragment(R.layout.fragment_popular), OnItemClickPopularListener {

    private val movieViewModel: MovieViewModel by viewModel()

    private lateinit var binding: FragmentPopularBinding

    private val myAdapter = MoviePagingAdapter(this@PopularFragment)

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

        val search = menu.findItem(R.id.action_bar_search)
        val searchView = search?.actionView as SearchView
        searchView.queryHint = "Pesquisar Filmes"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(query: String?): Boolean {
                query?.let {
                    movieViewModel.setQuery(it)
                }
                return true
            }
        })
    }

    override fun onClick(movie: MovieDto) {
        var output: LocalDate? = null
        var realeseMovie = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var simpleFormat2 = DateTimeFormatter.ISO_DATE
            output = LocalDate.parse(movie!!.dataLancamento, simpleFormat2)
            realeseMovie = "${output.dayOfMonth}/${output.monthValue}/${output.year}"
        }

        val action = ViewPageFragmentDirections.actionViewPageFragmentToDetailsFragment(
            movie,
            realeseMovie
        )
        findNavController().navigate(action)
    }

    private fun initView() {
        binding.apply {
            progressBarPopular.visibility = View.VISIBLE
            initMovieObserve()
            movieViewModel.input.getAllMovies(null)
            refreshPopular.setOnRefreshListener {
                movieViewModel.setBlank()
                refreshPopular.isRefreshing = false
            }

            movieViewModel.list.observe(viewLifecycleOwner) {
                myAdapter.submitData(lifecycle, it)
                binding.progressBarPopular.visibility = View.GONE
            }
        }
    }

    private fun setupAdapter() {
        binding.recyclerViewPopular.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}