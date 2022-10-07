package com.example.movies.presentation.popular_and_favorite.screens.popular.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.databinding.ItemPopularBinding
import com.example.movies.domain.model.Movie
import com.example.movies.util.Utils

class PopularViewHolder(
    private val binding: ItemPopularBinding,
    private val onClick: (movie : Movie) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        Glide.with(itemView)
            .load(movie.posterUrl)
            .into(binding.imgMoviePopular)

        binding.txtMovieTitlePopular.text = movie.title
        binding.txtMovieDescriptionPopular.text = movie.overview
        binding.txtReleaseDatePopular.text = itemView.context
            .getString(R.string.label_realese_data, Utils.showDate(movie.releaseData))

        itemView.setOnClickListener { onClick(movie) }
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