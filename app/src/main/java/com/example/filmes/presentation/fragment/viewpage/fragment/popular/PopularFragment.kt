package com.example.filmes.presentation.fragment.viewpage.fragment.popular

import android.os.Bundle
import android.view.View
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

class PopularFragment : Fragment(R.layout.fragment_popular) , OnItemClickPopularListener {

    private val movieViewModel: MovieViewModel by viewModel()
    lateinit var movieList:ArrayList<MovieDto>

    private lateinit var binding: FragmentPopularBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPopularBinding.bind(view)
        initView()
    }

    private fun initView() {
        binding.apply {
            progressBarPopular.visibility = View.VISIBLE
            getPopularMovies()
            getErro()
            refreshPopular.setOnRefreshListener {
                movieViewModel.input.getAllMovies(null)
                edtSearchPopular.clearFocus()
                refreshPopular.isRefreshing = false
            }
            edtSearchPopular.addTextChangedListener { movieName ->
                movieViewModel.input.getAllMovies(movieName.toString())
            }
        }
    }

    fun getPopularMovies(){
        movieViewModel.input.getAllMovies(null)
        movieViewModel.output.movieList.observe(requireActivity()) { listaFilmes ->
            movieList = listaFilmes
            updateAdapter(movieList)
            binding.progressBarPopular.visibility = View.GONE
        }
    }

    private fun getErro() {
        movieViewModel.output.error.observe(requireActivity()) { erro ->
            //se for true o erro é de conexão
            if(erro)
                requireContext().showToast("Erro de conexão")
            else if(binding.edtSearchPopular.length() > 1)
                requireContext().showToast("Filme não encontrado")

        }
    }

    fun updateAdapter(listMovies: ArrayList<MovieDto>){
        var popularAdapter = PopularAdapter(this, listMovies)
        binding.recyclerViewPopular.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = popularAdapter
        }
    }

    override fun onClick(position: Int) {
        val action = ViewPageFragmentDirections.actionViewPageFragmentToDetailsFragment(movieList[position], null)
        findNavController().navigate(action)
    }
}