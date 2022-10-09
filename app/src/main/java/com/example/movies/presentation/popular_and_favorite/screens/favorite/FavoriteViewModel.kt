package com.example.movies.presentation.popular_and_favorite.screens.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.Movie
import com.example.core.usecase.local.DeleteMovieLocalCaseUse
import com.example.core.usecase.local.GetAllMoviesLocalUseCase
import com.example.core.usecase.local.SearchMovieLocalUseCase
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val deleteMovie: DeleteMovieLocalCaseUse,
    private val searchMovie: SearchMovieLocalUseCase,
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