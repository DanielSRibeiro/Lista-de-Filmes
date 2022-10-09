package com.example.movies.presentation.popular_and_favorite.screens.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.ErrorStates
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

    private var _progressBar = MutableLiveData<Boolean?>()
    val progressBar: LiveData<Boolean?> = _progressBar

    private var _errorNetwork = MutableLiveData<Boolean?>()
    val errorNetwork: LiveData<Boolean?> = _errorNetwork

    fun getAllMovies() {
        viewModelScope.launch {
            _progressBar.value = true
            val resource = getAllMovies.invoke()
            _progressBar.value = false
            when (resource) {
                is Resource.Success -> {
                    _list.value = resource.data
                    _errorNetwork.value = false
                }
                is Resource.Fail -> {
                    _list.value = null
                    if(resource.status == ErrorStates.NoInternet)
                        _errorNetwork.value = true
                }
            }
        }
    }

    fun seachMovie(query: String) {
        viewModelScope.launch {
            _progressBar.value = true
            val resource = searchMovies.invoke(query)
            _progressBar.value = false
            when (resource) {
                is Resource.Success -> {
                    _list.value = resource.data
                    _errorNetwork.value = false
                }
                is Resource.Fail -> {
                    _list.value = null
                    if(resource.status == ErrorStates.NoInternet)
                        _errorNetwork.value = true

                }
            }


        }
    }
}
