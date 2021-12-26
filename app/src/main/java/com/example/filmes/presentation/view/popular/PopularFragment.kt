package com.example.filmes.presentation.view.popular

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmes.R
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.presentation.view.adapter.OnItemClickPopularListener
import com.example.filmes.presentation.view.adapter.PopularAdapter
import com.example.filmes.presentation.view.viewpage.ViewPageFragmentDirections
import com.example.filmes.presentation.viewmodel.remote.MovieViewModel
import com.example.filmes.utilis.showToast
import kotlinx.android.synthetic.main.fragment_popular.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularFragment : Fragment(R.layout.fragment_popular) , OnItemClickPopularListener {

    private val movieViewModel: MovieViewModel by viewModel()
    lateinit var movieList:ArrayList<MovieDto>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        progressBar_popular.visibility = View.VISIBLE
        getPopularMovies()
        getErro()
        refresh_popular.setOnRefreshListener {
            movieViewModel.getAllMovies(null)
            edt_search_popular.clearFocus()
            refresh_popular.isRefreshing = false
        }
        edt_search_popular.addTextChangedListener { movieName ->
            movieViewModel.getAllMovies(movieName.toString())
        }
    }

    fun getPopularMovies(){
        movieViewModel.getAllMovies(null)
        movieViewModel.movieList.observe(requireActivity()) { listaFilmes ->
            movieList = listaFilmes
            updateAdapter(movieList)
            progressBar_popular.visibility = View.GONE
        }
    }

    private fun getErro() {
        movieViewModel.error.observe(requireActivity()) { erro ->
            //se for true o erro é de conexão
            if(erro)
                requireContext().showToast("Erro de conexão")
            else if(edt_search_popular.length() > 1)
                requireContext().showToast("Filme não encontrado")

        }
    }

    fun updateAdapter(listMovies: ArrayList<MovieDto>){
        var popularAdapter = PopularAdapter(this, listMovies)
        recyclerView_popular.layoutManager = LinearLayoutManager(activity)
        recyclerView_popular.adapter = popularAdapter
    }

    override fun onClick(position: Int) {
        val action = ViewPageFragmentDirections.actionViewPageFragmentToDetailsFragment(movieList[position], null)
        findNavController().navigate(action)
    }
}