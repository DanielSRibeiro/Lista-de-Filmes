package com.example.filmes.presentation.fragment.viewpage.fragment.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmes.databinding.PopularItemBinding
import com.example.filmes.utilis.BASE_IMAGEM
import com.example.filmes.domain.model.MovieDto
import java.text.SimpleDateFormat

class PopularAdapter(
    var listener: OnItemClickPopularListener,
    var movieList: ArrayList<MovieDto>
) : RecyclerView.Adapter<PopularAdapter.viewHolder>() {

    private lateinit var binding: PopularItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        binding = PopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        var movie = movieList[position]
        with(binding){
            var imageUrl = BASE_IMAGEM + movie.posterFilme
            Glide.with(root)
                .load(imageUrl)
                .into(imgMoviePopular)

            val formatDate = SimpleDateFormat("dd/MM/yyyy")
            val realeseDate = formatDate.format(movie.dataLancamento)

            txtMovieTitlePopular.text = movie.tituloFilme
            txtReleaseDatePopular.text = "Lançamento $realeseDate"
            txtMovieDescriptionPopular.text = movie.sinopse
        }
    }

    override fun getItemCount(): Int = movieList.size

    class viewHolder(binding: PopularItemBinding, listener: OnItemClickPopularListener) : RecyclerView.ViewHolder(binding.root){
        init {
            itemView.setOnClickListener { listener.onClick(adapterPosition) }
        }
    }
}
interface OnItemClickPopularListener {
    fun onClick(position : Int)
}