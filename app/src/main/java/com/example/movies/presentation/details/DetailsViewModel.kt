package com.example.movies.presentation.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.model.Movie
import com.example.movies.domain.usecase.local.DeleteMovieCaseUse
import com.example.movies.domain.usecase.local.GetCategoryUseCase
import com.example.movies.domain.usecase.local.InsertMovieUseCase
import com.example.movies.domain.usecase.local.CheckMovieStateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(
    private val getCategory: GetCategoryUseCase,
    private val insertMovie: InsertMovieUseCase,
    private val checkMovieState: CheckMovieStateUseCase,
    private val deleteMovie: DeleteMovieCaseUse,
) : ViewModel() {

    private val _categoriesNames = MutableLiveData<String>()
    val categoriesNames: LiveData<String> = _categoriesNames

    private var _checkState = MutableLiveData<Boolean>()
    val checkState: LiveData<Boolean> = _checkState

    fun check(movie: Movie) {
        viewModelScope.launch {
            val resource = withContext(Dispatchers.Default) {
                try {
                    checkMovieState.invoke(movie.id)
                } catch (ex: Exception) {
                    Log.d("TAG", "addContato: $ex")
                    false
                }
            }
            _checkState.value = resource
        }
    }

    fun checkState(movie: Movie){
        viewModelScope.launch {
            val resource = withContext(Dispatchers.Default) {
                try {
                    checkMovieState.invoke(movie.id)
                } catch (ex: Exception) {
                    Log.d("TAG", "addContato: $ex")
                    false
                }
            }

            if(resource)
                deleteMovie(movie.id)
            else
                insertMovie(movie)
        }
    }

    fun getCategories(movie: Movie) {
        viewModelScope.launch {
            val categoriesList = getCategory.invoke()
            var names = ""

            categoriesList.forEach { category ->
                movie.genreIds.forEach {
                    if (category.id == it) {
                        names += category.name + ", "
                    }
                }
            }
            _categoriesNames.value = names
        }
    }

    private fun insertMovie(movie: Movie) {
        viewModelScope.launch {
            try {
                insertMovie.invoke(movie)
            } catch (ex: Exception) {
                Log.d("TAG", "insertMovie: $ex")
            }

            _checkState.value = true
        }
    }

    private fun deleteMovie(id: Int) {
        viewModelScope.launch {
            try {
                deleteMovie.invoke(id)
            } catch (ex: Exception) {
                Log.d("TAG", "insertMovie: $ex")
            }

            _checkState.value = false
        }
    }
}