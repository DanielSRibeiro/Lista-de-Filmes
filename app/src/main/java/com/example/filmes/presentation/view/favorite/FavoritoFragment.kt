package com.example.filmes.presentation.view.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmes.R
import com.example.filmes.data.local.entity.MovieEntity
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.presentation.view.DetalhesActivity
import com.example.filmes.presentation.view.adapter.FavoritoAdapter
import com.example.filmes.presentation.view.adapter.OnItemClickFavoritoListener
import com.example.filmes.presentation.viewmodel.local.DeleteViewModel
import com.example.filmes.presentation.viewmodel.local.SelectViewModel
import com.example.filmes.utilis.JsonService
import kotlinx.android.synthetic.main.fragment_favorito.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class FavoritoFragment : Fragment() , OnItemClickFavoritoListener {

    lateinit var listMovieSalvo:List<MovieEntity>
    private val selectViewModel:SelectViewModel by viewModel()
    private val deleteViewModel: DeleteViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorito, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        getListaFilmes()
        refresh_favorite.setOnRefreshListener {
            selectViewModel.getSeachMovie(null)
            refresh_favorite.isRefreshing = false
        }
        edt_search_favorite.addTextChangedListener { nome ->
            selectViewModel.getSeachMovie(nome.toString())
        }
    }

    private fun getListaFilmes() {
        selectViewModel.getSeachMovie(null)
        selectViewModel.listaSalva.observe(requireActivity()){ listaEntity ->
            listMovieSalvo = listaEntity
            updateAdapter(listMovieSalvo)
            if(listaEntity.isNotEmpty())
                txt_none_favorite.visibility = View.GONE
            else
                txt_none_favorite.visibility = View.VISIBLE
        }
    }

    private fun updateAdapter(listMovieSalvo : List<MovieEntity>) {
        recyclerView_favorite.layoutManager = LinearLayoutManager(activity)
        recyclerView_favorite.adapter = FavoritoAdapter(this, listMovieSalvo)
    }

    override fun onClick(posicao: Int) {
        var entity = listMovieSalvo[posicao]
        var generos = JsonService.fromIntArray(entity.generosIds)
        var movieDto = MovieDto(
            id = entity.id.toInt(), posterFilme = entity.posterFilme, tituloFilme = entity.tituloFilme,
            sinopse = entity.sinopse, notaMedia = entity.notaMedia,
            adult = entity.adult, backdropPath = entity.backdropPath, originalLanguage = entity.originalLanguage,
            originalTitle = entity.originalTitle, popularity = entity.popularity, video = entity.video ,
            voteCount = entity.voteCount, generosIds = generos,
            dataLancamento = Date()
        )

        var intent = Intent(activity, DetalhesActivity::class.java)
        intent.putExtra(R.string.KEY_MOVIE.toString(), movieDto)
        intent.putExtra("data_string", entity.dataLancamento)
        startActivity(intent)
    }

    override fun onClickButton(movie: MovieEntity) {
        deleteViewModel.deleteMovie(movie.id.toInt())
        selectViewModel.getSeachMovie(null)
    }
}