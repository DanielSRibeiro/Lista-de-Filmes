package com.example.movies.presentation.fragment.popular_and_favorite.screens.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Resource
import com.example.movies.domain.usecase.remote.MovieUseCase
import kotlinx.coroutines.launch

class MovieViewModel(
    private var getMovie: MovieUseCase
) : ViewModel() {

    var _list = MutableLiveData<List<Movie>?>()
    val list : LiveData<List<Movie>?> = _list

    fun getAllMovies(name: String?) {
        viewModelScope.launch {
            val resource = getMovie(name)
            if(resource is Resource.Success){
                _list.value = resource.data

            }
        }
    }

}