package com.example.filmes.presentation.fragment.popular_and_favorite.screens.popular.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmes.databinding.PopularItemBinding
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.utilis.BASE_IMAGEM

class PopularViewHolder(
    popularItemBinding: PopularItemBinding,
    private val onClick: (movie : MovieDto) -> Unit
) : RecyclerView.ViewHolder(popularItemBinding.root) {

    var imgMoviePopular = popularItemBinding.imgMoviePopular
    var txtMovieTitlePopular = popularItemBinding.txtMovieTitlePopular
    var txtReleaseDatePopular = popularItemBinding.txtReleaseDatePopular
    var txtMovieDescriptionPopular = popularItemBinding.txtMovieDescriptionPopular

    fun bind(movie: MovieDto) {
        Glide.with(itemView)
            .load(BASE_IMAGEM + movie.posterFilme)
            .into(imgMoviePopular)

        txtMovieTitlePopular.text = movie.tituloFilme
        txtReleaseDatePopular.text = movie.dataLancamento
        txtMovieDescriptionPopular.text = movie.sinopse

        itemView.setOnClickListener {
            onClick(movie)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup, onClick: (movie : MovieDto) -> Unit
        ) : PopularViewHolder {
            val inflate = LayoutInflater.from(parent.context)
            val popularItemBinding = PopularItemBinding.inflate(inflate, parent, false)
            return PopularViewHolder(popularItemBinding, onClick)
        }
    }
}