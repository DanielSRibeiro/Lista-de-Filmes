package com.example.filmes.presentation.fragment.viewpage.fragment.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.domain.model.ResultsMoviesDto
import com.example.filmes.domain.usecase.remote.MovieUseCase
import kotlinx.coroutines.launch

class MovieViewModel(
    private var getMovie: MovieUseCase
) : ViewModel() {

    var _list = MutableLiveData<ResultsMoviesDto?>()
    val list : LiveData<ResultsMoviesDto?> = _list

    fun getAllMovies(name: String?) {
        viewModelScope.launch {
            val response = getMovie(name)
            _list.value = response
        }
    }

}