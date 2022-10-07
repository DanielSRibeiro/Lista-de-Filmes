package com.example.movies.presentation.popular_and_favorite.screens.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.model.Movie
import com.example.movies.domain.usecase.local.DeleteMovieCaseUse
import com.example.movies.domain.usecase.local.SelectMovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(
    private val deleteMovieUseCase: DeleteMovieCaseUse,
    private val selectMovieUseCase: SelectMovieUseCase
) : ViewModel() {

    private val _savedList = MutableLiveData<List<Movie>?>()
    val savedList: LiveData<List<Movie>?> = _savedList

    fun deleteMovie(id: Int) {
        viewModelScope.launch {
            try {
                deleteMovieUseCase.invoke(id)
                getSeachMovie(null)
            } catch (ex: Exception) {
                Log.d("TAG", "insertMovie: $ex")
            }
        }
    }


    fun getSeachMovie(title: String?) {
        viewModelScope.launch {
            val lista = withContext(Dispatchers.Default) {
                try {
                    selectMovieUseCase.invoke(title)
                } catch (ex: Exception) {
                    Log.d("TAG", "getSeachMovie: $ex")
                    listOf()
                }
            }
            _savedList.value = lista
        }
    }
}