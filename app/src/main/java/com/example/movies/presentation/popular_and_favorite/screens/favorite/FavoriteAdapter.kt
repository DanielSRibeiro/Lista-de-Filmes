package com.example.movies.presentation.popular_and_favorite.screens.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.databinding.ItemFavoriteBinding
import com.example.movies.domain.model.Movie
import com.example.movies.util.Utils

class FavoriteAdapter(
    var movieList: List<Movie>
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private lateinit var binding: ItemFavoriteBinding

    private var onClick: (movie: Movie) -> Unit = {}
    private var onClickButton: (movie: Movie) -> Unit = {}

    fun setOnClick(action: (movie: Movie) -> Unit) {
        onClick = action
    }

    fun setOnClickButton(action: (movie: Movie) -> Unit) {
        onClickButton = action
    }

    fun update(list: List<Movie>) {
        this.movieList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val movie = movieList[position]

        Glide.with(binding.root)
            .load(movie.posterUrl)
            .into(binding.imgMovieFavorite)

        binding.txtMovieTitleFavorite.text = movie.title
        binding.txtReleaseDateFavorite.text =
            binding.root.context.getString(R.string.label_realese_data, Utils.showDate(movie.releaseData))

        binding.txtMovieDescriptionFavorite.text = movie.overview

        binding.bntFavorite.setOnClickListener { onClickButton(movie) }
        holder.itemView.setOnClickListener { onClick(movie) }
    }

    override fun getItemCount(): Int = movieList.size

    class FavoriteViewHolder(
        binding: ItemFavoriteBinding
    ) : RecyclerView.ViewHolder(binding.root)
}