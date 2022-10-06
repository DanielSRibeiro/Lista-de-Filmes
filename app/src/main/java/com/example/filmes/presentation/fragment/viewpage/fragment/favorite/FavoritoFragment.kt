package com.example.filmes.presentation.fragment.viewpage.fragment.favorite

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.filmes.data.local.entity.MovieEntity
import com.example.filmes.databinding.FragmentFavoritoBinding
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.presentation.fragment.LocalViewModel
import com.example.filmes.presentation.fragment.viewpage.ViewPageFragmentDirections
import com.example.filmes.utilis.IOnAction
import com.example.filmes.utilis.JsonService
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class FavoritoFragment : Fragment(), IOnAction{

    private var _binding: FragmentFavoritoBinding? = null
    private val binding get() = _binding!!

    private val localViewModel: LocalViewModel by viewModel()

    private lateinit var listMovieSalvo:List<MovieEntity>
    private lateinit var searchView: SearchView

    lateinit var favoritoAdapter: FavoritoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getListaFilmes()

        binding.apply {
            refreshFavorite.setOnRefreshListener {
                localViewModel.input.getSeachMovie(null)
                refreshFavorite.isRefreshing = false
            }
        }
    }

    private fun getListaFilmes() {
        localViewModel.input.getSeachMovie(null)
        localViewModel.output.listaSalva.observe(requireActivity()){ listaEntity ->
            listMovieSalvo = listaEntity
            setupAdapter(listMovieSalvo)

            binding.txtNoneFavorite.visibility =
                if(listaEntity.isNotEmpty()) View.GONE
                else View.VISIBLE
        }
    }

    private fun setupAdapter(listMovieSalvo : List<MovieEntity>) {
        binding.recyclerViewFavorite.apply {
            favoritoAdapter = FavoritoAdapter(listMovieSalvo)
            favoritoAdapter.setOnClick { onClick(it) }
            favoritoAdapter.setOnClickButton { onClickButton(it) }
            adapter = favoritoAdapter
        }
    }

    private fun onClick(position: Int) {
        val entity = listMovieSalvo[position]
        val generos = JsonService.fromIntArray(entity.generosIds)
        val movieDto = MovieDto(
            id = entity.id.toInt(), posterFilme = entity.posterFilme, tituloFilme = entity.tituloFilme,
            sinopse = entity.sinopse, notaMedia = entity.notaMedia,
            adult = entity.adult, backdropPath = entity.backdropPath, originalLanguage = entity.originalLanguage,
            originalTitle = entity.originalTitle, popularity = entity.popularity, video = entity.video ,
            voteCount = entity.voteCount, generosIds = generos,
            dataLancamento = Date().toString()
        )

        val action = ViewPageFragmentDirections.actionViewPageFragmentToDetailsFragment(movieDto, entity.dataLancamento)
        findNavController().navigate(action)
    }

    private fun onClickButton(movie: MovieEntity) {
        localViewModel.input.deleteMovie(movie.id.toInt())
        localViewModel.input.getSeachMovie(null)
    }

    override fun executeAction(name: String?) {
        if (name.isNullOrBlank())
            localViewModel.input.getSeachMovie(name)
        else
            localViewModel.input.getSeachMovie(null)
        binding.txtNoneFavorite.text = "Filme não encontrado"
    }
}