package com.example.filmes.presentation.fragment.popular_and_favorite.screens.popular.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.filmes.domain.model.MovieDto

class PopularAdapter(
    private val onClick: (movie : MovieDto) -> Unit
) : ListAdapter<MovieDto, PopularViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<MovieDto>() {
            override fun areItemsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean {
                return oldItem == newItem
            }
        }
    }
}