package com.example.filmes.presentation.fragment.viewpage.fragment.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmes.databinding.PopularItemBinding
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.utilis.BASE_IMAGEM
import java.text.SimpleDateFormat

class MoviePagingAdapter(
    var listener: OnItemClickPopularListener
) : PagingDataAdapter<MovieDto, MoviePagingAdapter.viewHolder>(DIFF_UTIL) {

    private lateinit var binding: PopularItemBinding

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<MovieDto>(){
            override fun areItemsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        binding = PopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let { holder.bind(movie) }
    }

    class viewHolder(var binding: PopularItemBinding, var listener: OnItemClickPopularListener) : RecyclerView.ViewHolder(binding.root){

        fun bind(movie:MovieDto){
            with(binding){
                val imageUrl = BASE_IMAGEM + movie.posterFilme
                Glide.with(root)
                    .load(imageUrl)
                    .into(imgMoviePopular)

                val formatDate = SimpleDateFormat("dd/MM/yyyy")
//                val realeseDate = formatDate.format(movie!!.dataLancamento).toString()

//                txtReleaseDatePopular.text = "Lançamento $realeseDate"
                txtMovieTitlePopular.text = movie.tituloFilme
                txtMovieDescriptionPopular.text = movie.sinopse
            }
            itemView.setOnClickListener { listener.onClick(movie) }
        }

    }
}
interface OnItemClickPopularListener {
    fun onClick(movie: MovieDto)
}