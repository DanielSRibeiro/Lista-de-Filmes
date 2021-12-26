package com.example.filmes.presentation.viewmodel.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.domain.usecase.remote.GetMovie
import com.example.filmes.domain.usecase.remote.MovieUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel(
    private var getMovie: MovieUseCase
) : ViewModel() {

    private val mMovieList = MutableLiveData<ArrayList<MovieDto>>()
    val movieList: LiveData<ArrayList<MovieDto>>
        get() = mMovieList

    private val mError = MutableLiveData<Boolean>()
    val error: LiveData<Boolean>
        get() = mError

    fun getAllMovies(name:String?){
        CoroutineScope(Dispatchers.Main).launch {
            var resultsMovies = withContext(Dispatchers.Default) {
                    getMovie.invoke(name)
                }

            if(!resultsMovies.movieList.isNullOrEmpty())
                mMovieList.value = resultsMovies.movieList
            else
                mError.value = name == null
        }
    }

}