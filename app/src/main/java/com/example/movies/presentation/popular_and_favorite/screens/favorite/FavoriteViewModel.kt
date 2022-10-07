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


    fun getSeachMovie(title: String) {
        viewModelScope.launch {
            val resource = withContext(Dispatchers.Default) {
                try {
                    searchMovie.invoke(title)
                } catch (ex: Exception) {
                    Log.d("TAG", "getSeachMovie: $ex")
                    listOf()
                }
            }
            _savedList.value = resource
        }
    }
    
    fun getAllMovie() {
        viewModelScope.launch {
            val resource = withContext(Dispatchers.Default) {
                try {
                    getAllMovies.invoke()
                } catch (ex: Exception) {
                    Log.d("TAG", "getSeachMovie: $ex")
                    listOf()
                }
            }
            _savedList.value = resource
        }
    }
    
}