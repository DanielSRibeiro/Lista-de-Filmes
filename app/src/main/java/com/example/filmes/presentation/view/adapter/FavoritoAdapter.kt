package com.example.filmes.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmes.R
import com.example.filmes.data.api.RetrofitTask
import com.example.filmes.domain.model.MovieDto
import kotlinx.android.synthetic.main.favorite_item.view.*
import java.text.SimpleDateFormat

class FavoritoAdapter(
    var listener: OnItemClickFavoritoListener,
    var movieList: ArrayList<MovieDto>
) : RecyclerView.Adapter<FavoritoAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_item, parent, false)
        return viewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        var movie = movieList[position]
        with(holder.itemView){
            var imageUrl = RetrofitTask.BASE_IMAGEM + movie.posterFilme
            Glide.with(this).load(imageUrl).into(img_movie_favorite)

            val formatDate = SimpleDateFormat("dd/MM/yyyy")
            val realeseDate = formatDate.format(movie.dataLancamento)

            txt_movie_title_favorite.text = movie.tituloFilme
            txt_release_date_favorite.text = realeseDate
            txt_movie_description_favorite.text = movie.sinopse

            bnt_favorite.setOnClickListener { listener.onClickButtonFavorito(movie) }
        }
    }

    override fun getItemCount(): Int = movieList.size

    class viewHolder(itemView: View, listener: OnItemClickFavoritoListener) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener { listener.onClick(adapterPosition) }
        }
    }

}
interface OnItemClickFavoritoListener {
    fun onClick(position : Int)

    fun onClickButtonFavorito(movie: MovieDto)
}