package com.example.movies.presentation.fragment.popular_and_favorite.screens.popular

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.movies.databinding.FragmentPopularBinding
import com.example.movies.presentation.fragment.popular_and_favorite.PopularAndFavoriteFragmentDirections
import com.example.movies.presentation.fragment.popular_and_favorite.screens.popular.adapter.PopularAdapter
import com.example.movies.utilis.IOnAction
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PopularFragment : Fragment(), IOnAction {

    private val movieViewModel: MovieViewModel by viewModel()

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    lateinit var myAdapter: PopularAdapter

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
        setupAdapter()
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        binding.apply {
            progressBarPopular.visibility = View.VISIBLE
            movieViewModel.getAllMovies(null)
            initMovieObserve()
            refreshPopular.setOnRefreshListener {
                movieViewModel.getAllMovies(null)
                refreshPopular.isRefreshing = false
            }

        }
    }

    private fun initMovieObserve() {
        movieViewModel.list.observe(viewLifecycleOwner) {
            it?.let { result ->
                myAdapter.submitList(result)
                binding.progressBarPopular.visibility = View.GONE
            }
        }
    }

    private fun setupAdapter() {
        binding.recyclerViewPopular.apply {
            myAdapter = PopularAdapter { movie ->
                var output: LocalDate? = null
                var realeseMovie = ""
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !movie.releaseData.isNullOrBlank()) {
                    val simpleFormat2 = DateTimeFormatter.ISO_DATE
                    output = LocalDate.parse(movie!!.releaseData, simpleFormat2)
                    realeseMovie = "${output.dayOfMonth}/${output.monthValue}/${output.year}"
                }

                val action = PopularAndFavoriteFragmentDirections.actionViewPageFragmentToDetailsFragment(
                    movie,
                    realeseMovie
                )
                findNavController().navigate(action)
            }

            adapter = myAdapter
        }
    }

    override fun executeAction(string: String?) {
//        movieViewModel.setQuery(it)
    }
}