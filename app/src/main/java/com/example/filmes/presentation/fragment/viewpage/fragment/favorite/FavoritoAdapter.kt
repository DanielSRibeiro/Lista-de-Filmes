package com.example.filmes.presentation.fragment.viewpage.fragment.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmes.data.local.entity.MovieEntity
import com.example.filmes.databinding.FavoriteItemBinding
import com.example.filmes.utilis.BASE_IMAGEM

class FavoritoAdapter(
    var listener: OnItemClickFavoritoListener,
    var movieList: List<MovieEntity>
) : RecyclerView.Adapter<FavoritoAdapter.viewHolder>() {

    private lateinit var binding: FavoriteItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        binding = FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val movie = movieList[position]

        with(binding){
            val imageUrl = BASE_IMAGEM + movie.posterFilme
            Glide.with(root)
                .load(imageUrl)
                .into(imgMovieFavorite)

            txtMovieTitleFavorite.text = movie.tituloFilme
            txtReleaseDateFavorite.text = "Lançamento ${movie.dataLancamento}"
            txtMovieDescriptionFavorite.text = movie.sinopse

            bntFavorite.setOnClickListener { listener.onClickButton(movie) }
        }
    }

    override fun getItemCount(): Int = movieList.size

    class viewHolder(binding: FavoriteItemBinding, listener: OnItemClickFavoritoListener) : RecyclerView.ViewHolder(binding.root){
        init {
            itemView.setOnClickListener { listener.onClick(adapterPosition) }
        }
    }
}

interface OnItemClickFavoritoListener {
    fun onClick(position : Int)
    fun onClickButton(movie: MovieEntity)
}