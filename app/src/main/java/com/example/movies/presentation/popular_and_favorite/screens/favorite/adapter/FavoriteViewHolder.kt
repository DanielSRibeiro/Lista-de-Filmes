package com.example.movies.presentation.popular_and_favorite.screens.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.databinding.ItemFavoriteBinding
import com.example.core.domain.model.Movie
import com.example.movies.util.Utils

class FavoriteViewHolder(
    private val binding: ItemFavoriteBinding,
    private var onClick: (movie: Movie) -> Unit,
    private var onClickButton: (movie: Movie) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        Glide.with(itemView)
            .load(movie.posterUrl)
            .into(binding.imgMovieFavorite)

        binding.txtMovieTitleFavorite.text = movie.title
        binding.txtMovieDescriptionFavorite.text = movie.overview
        binding.txtReleaseDateFavorite.text = itemView.context
            .getString(R.string.label_realese_data, Utils.showDate(movie.releaseData))

        binding.bntFavorite.setOnClickListener { onClickButton(movie) }
        itemView.setOnClickListener { onClick(movie) }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClick: (movie: Movie) -> Unit,
            onClickButton: (movie: Movie) -> Unit
        ) : FavoriteViewHolder {
            val inflate = LayoutInflater.from(parent.context)
            val itemFavoriteBinding = ItemFavoriteBinding.inflate(inflate, parent, false)

            return FavoriteViewHolder(
                itemFavoriteBinding,
                onClick,
                onClickButton
            )
        }
    }
}