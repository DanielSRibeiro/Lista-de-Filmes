package com.example.filmes.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.filmes.R
import com.example.filmes.utilis.BASE_IMAGEM
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.presentation.view.main.MainActivity
import com.example.filmes.presentation.viewmodel.remote.CategoriesViewModel
import com.example.filmes.presentation.viewmodel.local.DeleteViewModel
import com.example.filmes.presentation.viewmodel.local.InsertViewModel
import com.example.filmes.presentation.viewmodel.local.VerificarViewModel
import kotlinx.android.synthetic.main.activity_detalhes.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat


class DetalhesActivity : AppCompatActivity() {

    private val insertViewModel:InsertViewModel by viewModel()
    private val verificarMovieViewModel:VerificarViewModel by viewModel()
    private val deleteMovieViewModel:DeleteViewModel by viewModel()
    private val categoriesViewModel: CategoriesViewModel by viewModel()
    lateinit var movie: MovieDto
    var paraDeleta = false
    var realeseDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        initView()
    }

    private fun initView() {
        setupActionBar()
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

    private fun setupActionBar() {
        setSupportActionBar(toolbar_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupMovie() {
        movie = intent.getParcelableExtra(R.string.KEY_MOVIE.toString())!!
        verificarMovieViewModel.verificar(movie.id)
        var dataString = intent.getStringExtra("data_string")
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
        verificarMovieViewModel.verificado.observe(this) {foiSalvo ->
            var imagemInt = if(foiSalvo) R.drawable.favorito
            else R.drawable.nao_favorito

            this.paraDeleta = foiSalvo
            floating_save_details.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), imagemInt))
        }
    }

    private fun setupCategories(){
        categoriesViewModel.getCategories(movie)
        categoriesViewModel.categories.observe(this) { genres ->
            txt_movie_genre_details.text = genres
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        return super.onSupportNavigateUp()
    }
}