package com.example.filmes.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmes.R
import com.example.filmes.data.api.RetrofitTask
import com.example.filmes.domain.model.MovieDto
import kotlinx.android.synthetic.main.popular_item.view.*
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

class PopularAdapter(
    var listener: OnItemClickPopularListener,
    var listMovies: ArrayList<MovieDto>
) : RecyclerView.Adapter<PopularAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.popular_item, parent, false)
        return viewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        var filme = listMovies[position]
        with(holder.itemView){
            var urlImagem = RetrofitTask.BASE_IMAGEM + filme.backdropPath
            Glide.with(this).load(urlImagem).into(img_movie_popular)

            val formatoData = SimpleDateFormat("dd/MM/yyyy")
            val dataLancamento = formatoData.format(filme.dataLancamento)

            txt_release_date.text = "Lançamento: $dataLancamento"
            txt_movie_title_popular.text = filme.tituloFilme
        }
    }

    override fun getItemCount(): Int = listMovies.size

    class viewHolder(itemView: View, listener: OnItemClickPopularListener): RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener { listener.onClick(adapterPosition) }
        }
    }

}
interface OnItemClickPopularListener {
    fun onClick(posicao : Int)
}