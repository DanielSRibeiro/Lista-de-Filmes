package com.example.filmes.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmes.R
import com.example.filmes.utilis.BASE_IMAGEM
import com.example.filmes.domain.model.MovieDto
import kotlinx.android.synthetic.main.popular_item.view.*
import java.text.SimpleDateFormat

class PopularAdapter(
    var listener: OnItemClickPopularListener,
    var movieList: ArrayList<MovieDto>
) : RecyclerView.Adapter<PopularAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.popular_item, parent, false)
        return viewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        var movie = movieList[position]
        with(holder.itemView){
            var imageUrl = BASE_IMAGEM + movie.posterFilme
            Glide.with(this)
                .load(imageUrl)
                .into(img_movie_popular)

            val formatDate = SimpleDateFormat("dd/MM/yyyy")
            val realeseDate = formatDate.format(movie.dataLancamento)

            txt_movie_title_popular.text = movie.tituloFilme
            txt_release_date_popular.text = "Lançamento $realeseDate"
            txt_movie_description_popular.text = movie.sinopse
        }
    }

    override fun getItemCount(): Int = movieList.size

    class viewHolder(itemView: View, listener:OnItemClickPopularListener) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener { listener.onClick(adapterPosition) }
        }
    }
}
interface OnItemClickPopularListener {
    fun onClick(position : Int)
}