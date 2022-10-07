package com.example.movies.presentation.popular_and_favorite.screens.favorite.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.domain.model.Movie
import com.example.movies.presentation.popular_and_favorite.screens.favorite.adapter.FavoriteViewHolder

class FavoriteAdapter(
    var movieList: List<Movie>
) : RecyclerView.Adapter<FavoriteViewHolder>() {

    private var onClick: (movie: Movie) -> Unit = {}
    private var onClickButton: (movie: Movie) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder.create(parent, onClick, onClickButton)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = movieList.size

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
}