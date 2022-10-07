package com.example.filmes.presentation.fragment.popular_and_favorite.screens.popular.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmes.BuildConfig
import com.example.filmes.databinding.PopularItemBinding
import com.example.filmes.data.network.model.MovieDto

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
            .load(BuildConfig.BASE_IMAGEM + movie.posterPath)
            .into(imgMoviePopular)

        txtMovieTitlePopular.text = movie.title
        txtReleaseDatePopular.text = movie.releaseData
        txtMovieDescriptionPopular.text = movie.overview

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