package com.example.filmes.presentation.fragment.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.filmes.R
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.presentation.MainActivity
import com.example.filmes.presentation.fragment.LocalViewModel
import com.example.filmes.utilis.BASE_IMAGEM
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args: DetailsFragmentArgs by navArgs()
    private val localViewModel: LocalViewModel by viewModel()

    private lateinit var movie: MovieDto
    private var dataString:String? = null
    private var paraDeleta = false
    private var realeseDate = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        (activity as MainActivity).supportActionBar?.hide()
        setupMovie()
        setupFavorito()
        setupCategories()

        floating_save_details.setOnClickListener {
            if(paraDeleta)
                localViewModel.input.deleteMovie(movie.id)
            else
                localViewModel.input.insertMovie(movie, realeseDate)
        }

        back_navigation.setOnClickListener {
            (activity as MainActivity).supportActionBar?.show()
            findNavController().popBackStack()
        }
    }

    private fun setupMovie() {
        movie = args.movie
        dataString = args.dataLancamento

        localViewModel.input.verificar(movie.id)

        Glide.with(this)
            .load("${BASE_IMAGEM + movie.backdropPath}")
            .into(img_movie_details)

        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        realeseDate = dateFormat.format(movie.dataLancamento)

        txt_movie_note_details.text = "${movie.notaMedia}/10 \nAvaliação"
        txt_movie_title_details.text = movie.tituloFilme
        txt_movie_description_details.text = movie.sinopse
        txt_movie_date_details.text =
            if (dataString == null)
                "Lançamento: $realeseDate"
            else
                dataString
    }

    private fun setupFavorito() {
        localViewModel.output.verificado.observe(requireActivity()) { foiSalvo ->
            this.paraDeleta = foiSalvo
            floating_save_details.isSelected = foiSalvo
        }
    }

    private fun setupCategories(){
        localViewModel.input.getCategories(movie)
        localViewModel.output.categories.observe(requireActivity()) { genres ->
            txt_movie_genre_details.text = genres
        }
    }
}