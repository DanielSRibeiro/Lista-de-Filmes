package com.example.movies.presentation.fragment.popular_and_favorite.screens.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.BuildConfig
import com.example.movies.databinding.ItemFavoriteBinding
import com.example.movies.domain.model.Movie
import com.example.movies.util.Utils

class FavoriteAdapter(
    var movieList: List<Movie>
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private lateinit var binding: ItemFavoriteBinding

    private var onClick: (position : Int) -> Unit = {}
    private var onClickButton: (movie: Movie) -> Unit = {}

    fun setOnClick(action: (position : Int) -> Unit){
        onClick = action
    }

    fun setOnClickButton(action: (movie: Movie) -> Unit){
        onClickButton = action
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val movie = movieList[position]

        with(binding){
            Glide.with(root)
                .load(movie.posterUrl)
                .into(imgMovieFavorite)

            txtMovieTitleFavorite.text = movie.title
            txtReleaseDateFavorite.text = "Lançamento ${Utils.showDate(movie.releaseData)}"
            txtMovieDescriptionFavorite.text = movie.overview

            bntFavorite.setOnClickListener { onClickButton(movie) }
        }
    }

    override fun getItemCount(): Int = movieList.size

    class FavoriteViewHolder(
        binding: ItemFavoriteBinding,
        var onClick: (position : Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root){
        init {
            itemView.setOnClickListener { onClick(layoutPosition) }
        }
    }
}