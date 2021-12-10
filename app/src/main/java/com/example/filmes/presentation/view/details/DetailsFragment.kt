package com.example.filmes.presentation.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.filmes.R
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.presentation.viewmodel.local.DeleteViewModel
import com.example.filmes.presentation.viewmodel.local.InsertViewModel
import com.example.filmes.presentation.viewmodel.local.VerificarViewModel
import com.example.filmes.presentation.viewmodel.remote.CategoriesViewModel
import com.example.filmes.utilis.BASE_IMAGEM
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class DetailsFragment : Fragment() {

    val args: DetailsFragmentArgs by navArgs()
    private val insertViewModel: InsertViewModel by viewModel()
    private val verificarMovieViewModel: VerificarViewModel by viewModel()
    private val deleteMovieViewModel: DeleteViewModel by viewModel()
    private val categoriesViewModel: CategoriesViewModel by viewModel()
    lateinit var movie: MovieDto
    var paraDeleta = false
    var realeseDate = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        setupMovie()
        setupFavorito()
        setupCategories()
        floating_save_details.setOnClickListener {
            verificarMovieViewModel.verificar(movie.id)
            if(paraDeleta) deleteMovieViewModel.deleteMovie(movie.id)
            else insertViewModel.insertMovie(movie, realeseDate)

            verificarMovieViewModel.verificar(movie.id)
        }
    }

    private fun setupMovie() {
        movie = args.movie
        var dataString:String? = args.dataLancamento
        verificarMovieViewModel.verificar(movie.id)
        Glide.with(this).load(BASE_IMAGEM + movie.backdropPath+"").into(img_movie_details)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        realeseDate = dateFormat.format(movie.dataLancamento)
        txt_movie_note_details.text = "${movie.notaMedia}/10 \nAvaliação"
        txt_movie_title_details.text = movie.tituloFilme
        txt_movie_description_details.text = movie.sinopse

        //estava com dificuldade de transformar a data em Date, porque o atributo estava vindo do SQLite em extenso
        //em tão fiz dessa forma para conseguir
        if (dataString == null) txt_movie_date_details.text = "Lançamento: $realeseDate"
        else txt_movie_date_details.text = dataString
    }

    private fun setupFavorito() {
        verificarMovieViewModel.verificado.observe(requireActivity()) {foiSalvo ->
            this.paraDeleta = foiSalvo
            floating_save_details.isSelected = foiSalvo
        }
    }

    private fun setupCategories(){
        categoriesViewModel.getCategories(movie)
        categoriesViewModel.categories.observe(requireActivity()) { genres ->
            txt_movie_genre_details.text = genres
        }
    }

}