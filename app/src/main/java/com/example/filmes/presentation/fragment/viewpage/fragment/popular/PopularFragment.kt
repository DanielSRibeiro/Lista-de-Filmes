package com.example.filmes.presentation.fragment.viewpage.fragment.popular

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmes.R
import com.example.filmes.databinding.FragmentPopularBinding
import com.example.filmes.presentation.fragment.viewpage.ViewPageFragmentDirections
import com.example.filmes.presentation.fragment.viewpage.fragment.popular.adapter.PopularAdapter
import com.example.filmes.utilis.IOnAction
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
                myAdapter.submitList(result.movieList)
                binding.progressBarPopular.visibility = View.GONE
            }
        }
    }

    private fun setupAdapter() {
        binding.recyclerViewPopular.apply {
            myAdapter = PopularAdapter { movie ->
                var output: LocalDate? = null
                var realeseMovie = ""
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !movie.dataLancamento.isNullOrBlank()) {
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

            adapter = myAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun executeAction(string: String?) {
//        movieViewModel.setQuery(it)
    }
}