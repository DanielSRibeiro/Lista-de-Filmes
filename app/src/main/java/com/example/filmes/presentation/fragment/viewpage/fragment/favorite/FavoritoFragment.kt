package com.example.filmes.presentation.fragment.viewpage.fragment.favorite

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmes.R
import com.example.filmes.data.local.entity.MovieEntity
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.presentation.fragment.ViewModelLocal
import com.example.filmes.presentation.fragment.viewpage.ViewPageFragmentDirections
import com.example.filmes.utilis.JsonService
import kotlinx.android.synthetic.main.fragment_favorito.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class FavoritoFragment : Fragment(R.layout.fragment_favorito) , OnItemClickFavoritoListener {

    private val viewModelLocal: ViewModelLocal by viewModel()

    private lateinit var listMovieSalvo:List<MovieEntity>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getListaFilmes()

        refresh_favorite.setOnRefreshListener {
            viewModelLocal.getSeachMovie(null)
            refresh_favorite.isRefreshing = false
        }
        edt_search_favorite.addTextChangedListener { nome ->
            viewModelLocal.getSeachMovie(nome.toString())
        }
    }

    private fun getListaFilmes() {
        viewModelLocal.getSeachMovie(null)
        viewModelLocal.listaSalva.observe(requireActivity()){ listaEntity ->
            listMovieSalvo = listaEntity
            configAdapter(listMovieSalvo)

            txt_none_favorite.visibility =
                if(listaEntity.isNotEmpty()) View.GONE
                else View.VISIBLE
        }
    }

    private fun configAdapter(listMovieSalvo : List<MovieEntity>) {
        recyclerView_favorite.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = FavoritoAdapter(this@FavoritoFragment, listMovieSalvo)
        }
    }

    override fun onClick(position: Int) {
        var entity = listMovieSalvo[position]
        var generos = JsonService.fromIntArray(entity.generosIds)
        var movieDto = MovieDto(
            id = entity.id.toInt(), posterFilme = entity.posterFilme, tituloFilme = entity.tituloFilme,
            sinopse = entity.sinopse, notaMedia = entity.notaMedia,
            adult = entity.adult, backdropPath = entity.backdropPath, originalLanguage = entity.originalLanguage,
            originalTitle = entity.originalTitle, popularity = entity.popularity, video = entity.video ,
            voteCount = entity.voteCount, generosIds = generos,
            dataLancamento = Date()
        )

        val action = ViewPageFragmentDirections.actionViewPageFragmentToDetailsFragment(movieDto, entity.dataLancamento)
        findNavController().navigate(action)
    }

    override fun onClickButton(movie: MovieEntity) {
        viewModelLocal.deleteMovie(movie.id.toInt())
        viewModelLocal.getSeachMovie(null)
    }
}