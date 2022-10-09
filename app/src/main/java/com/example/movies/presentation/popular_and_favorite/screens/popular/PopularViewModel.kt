package com.example.movies.presentation.popular_and_favorite.screens.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.Resource
import com.example.core.domain.model.Movie
import com.example.core.usecase.remote.GetAllMoviesUseCase
import com.example.core.usecase.remote.SearchMoviesUseCase
import kotlinx.coroutines.launch

class PopularViewModel(
    private var searchMovies: SearchMoviesUseCase,
    private var getAllMovies: GetAllMoviesUseCase
) : ViewModel() {

    private var _list = MutableLiveData<List<Movie>?>()
    val list: LiveData<List<Movie>?> = _list

    fun getAllMovies() {
        viewModelScope.launch {
            val resource = getAllMovies.invoke()
            if (resource is Resource.Success) {
                _list.value = resource.data
            } else {
                _list.value = null
            }
        }
    }

    fun seachMovie(query: String) {
        viewModelScope.launch {
            val resource = searchMovies.invoke(query)
            if (resource is Resource.Success) {
                _list.value = resource.data
            } else {
                _list.value = null
            }
        }
    }
}
