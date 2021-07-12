package com.example.filmes.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmes.data.api.RetrofitTask
import com.example.filmes.data.repository.MovieImplementation
import com.example.filmes.domain.usecase.MovieUseCase
import com.example.filmes.domain.usecase.SearchUseCase
import com.example.filmes.domain.model.ResultsMoviesDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel(private var movieUseCase: MovieUseCase) : ViewModel() {

    var searchUseCase = SearchUseCase(MovieImplementation(RetrofitTask()))

    var movieListMutable = MutableLiveData<ResultsMoviesDto>()

    val movieLiveData: LiveData<ResultsMoviesDto>
        get() = movieListMutable

    fun getAllMovies(){
        CoroutineScope(Dispatchers.Main).launch {
            var movieList = withContext(Dispatchers.Default) {
                    movieUseCase.invoke()
                }
            movieListMutable.value = movieList
        }
    }

    fun searchMovie(nome:String){
        CoroutineScope(Dispatchers.Main).launch {
            var movieList = withContext(Dispatchers.Default) {
                searchUseCase.invoke(nome)
            }
            movieListMutable.value = movieList
        }
    }
}