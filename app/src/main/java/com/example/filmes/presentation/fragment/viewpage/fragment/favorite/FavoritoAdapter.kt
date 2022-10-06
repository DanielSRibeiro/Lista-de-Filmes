package com.example.filmes.presentation.fragment.viewpage.fragment.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmes.data.local.entity.MovieEntity
import com.example.filmes.databinding.FavoriteItemBinding
import com.example.filmes.utilis.BASE_IMAGEM

class FavoritoAdapter(
    var movieList: List<MovieEntity>
) : RecyclerView.Adapter<FavoritoAdapter.viewHolder>() {

    private lateinit var binding: FavoriteItemBinding

    private var onClick: (position : Int) -> Unit = {}
    private var onClickButton: (movie: MovieEntity) -> Unit = {}

    fun setOnClick(action: (position : Int) -> Unit){
        onClick = action
    }

    fun setOnClickButton(action: (movie: MovieEntity) -> Unit){
        onClickButton = action
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        binding = FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding, onClick)
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

            bntFavorite.setOnClickListener { onClickButton(movie) }
        }
    }

    override fun getItemCount(): Int = movieList.size

    class viewHolder(
        binding: FavoriteItemBinding,
        var onClick: (position : Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root){
        init {
            itemView.setOnClickListener { onClick(layoutPosition) }
        }
    }
}