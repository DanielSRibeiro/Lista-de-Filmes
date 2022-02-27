package com.example.filmes.presentation.fragment.viewpage.fragment.favorite

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmes.R
import com.example.filmes.data.local.entity.MovieEntity
import com.example.filmes.databinding.FragmentFavoritoBinding
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.presentation.fragment.LocalViewModel
import com.example.filmes.presentation.fragment.viewpage.ViewPageFragmentDirections
import com.example.filmes.utilis.JsonService
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class FavoritoFragment : Fragment(R.layout.fragment_favorito) , OnItemClickFavoritoListener {

    private lateinit var binding: FragmentFavoritoBinding
    private val localViewModel: LocalViewModel by viewModel()

    private lateinit var listMovieSalvo:List<MovieEntity>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoritoBinding.bind(view)

        getListaFilmes()

        binding.apply {
            refreshFavorite.setOnRefreshListener {
                localViewModel.input.getSeachMovie(null)
                refreshFavorite.isRefreshing = false
            }
            edtSearchFavorite.addTextChangedListener { nome ->
                localViewModel.input.getSeachMovie(nome.toString())
            }
        }
    }

    private fun getListaFilmes() {
        localViewModel.input.getSeachMovie(null)
        localViewModel.output.listaSalva.observe(requireActivity()){ listaEntity ->
            listMovieSalvo = listaEntity
            configAdapter(listMovieSalvo)

            binding.txtNoneFavorite.visibility =
                if(listaEntity.isNotEmpty()) View.GONE
                else View.VISIBLE
        }
    }

    private fun configAdapter(listMovieSalvo : List<MovieEntity>) {
        binding.recyclerViewFavorite.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = FavoritoAdapter(this@FavoritoFragment, listMovieSalvo)
        }
    }

    override fun onClick(position: Int) {
        val entity = listMovieSalvo[position]
        val generos = JsonService.fromIntArray(entity.generosIds)
        val movieDto = MovieDto(
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
        localViewModel.input.deleteMovie(movie.id.toInt())
        localViewModel.input.getSeachMovie(null)
    }
}