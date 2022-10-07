package com.example.movies.presentation.fragment.popular_and_favorite.screens.popular.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.BuildConfig
import com.example.movies.databinding.ItemPopularBinding
import com.example.movies.domain.model.Movie
import com.example.movies.util.Utils

class PopularViewHolder(
    popularItemBinding: ItemPopularBinding,
    private val onClick: (movie : Movie) -> Unit
) : RecyclerView.ViewHolder(popularItemBinding.root) {

    var imgMoviePopular = popularItemBinding.imgMoviePopular
    var txtMovieTitlePopular = popularItemBinding.txtMovieTitlePopular
    var txtReleaseDatePopular = popularItemBinding.txtReleaseDatePopular
    var txtMovieDescriptionPopular = popularItemBinding.txtMovieDescriptionPopular

    fun bind(movie: Movie) {
        Glide.with(itemView)
            .load(movie.posterUrl)
            .into(imgMoviePopular)

        txtMovieTitlePopular.text = movie.title
        txtReleaseDatePopular.text = "Lançamento ${Utils.showDate(movie.releaseData)}"
        txtMovieDescriptionPopular.text = movie.overview

        itemView.setOnClickListener {
            onClick(movie)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup, onClick: (movie : Movie) -> Unit
        ) : PopularViewHolder {
            val inflate = LayoutInflater.from(parent.context)
            val popularItemBinding = ItemPopularBinding.inflate(inflate, parent, false)
            return PopularViewHolder(popularItemBinding, onClick)
        }
    }
}