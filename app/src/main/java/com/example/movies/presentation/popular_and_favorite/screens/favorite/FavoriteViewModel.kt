package com.example.movies.presentation.popular_and_favorite.screens.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.model.Movie
import com.example.movies.domain.usecase.local.DeleteMovieCaseUse
import com.example.movies.domain.usecase.local.GetAllMoviesLocalUseCase
import com.example.movies.domain.usecase.local.SelectMovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(
    private val deleteMovie: DeleteMovieCaseUse,
    private val searchMovie: SelectMovieUseCase,
    private val getAllMovies: GetAllMoviesLocalUseCase,
) : ViewModel() {

    private val _savedList = MutableLiveData<List<Movie>?>()
    val savedList: LiveData<List<Movie>?> = _savedList

    fun getAllMovie() {
        viewModelScope.launch {
            val resource = getAllMovies.invoke()
            _savedList.value = resource.ifEmpty { null }
        }
    }

    fun seachMovie(title: String) {
        viewModelScope.launch {
            val resource = searchMovie.invoke(title)
            _savedList.value = resource.ifEmpty { null }
        }
    }

    fun deleteMovie(id: Int) {
        viewModelScope.launch {
            try {
                deleteMovie.invoke(id)
                getAllMovie()
            } catch (ex: Exception) {
                Log.d("TAG", "insertMovie: $ex")
            }
        }
    }
}