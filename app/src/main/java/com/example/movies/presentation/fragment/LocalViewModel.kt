package com.example.movies.presentation.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.model.Movie
import com.example.movies.domain.usecase.local.DeleteMovieCaseUse
import com.example.movies.domain.usecase.local.InsertMovieUseCase
import com.example.movies.domain.usecase.local.SelectMovieUseCase
import com.example.movies.domain.usecase.local.VerificarMovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocalViewModel(
    private val insertMovieUseCase: InsertMovieUseCase,
    private val verificarMovieUseCase: VerificarMovieUseCase,
    private val deleteMovieUseCase: DeleteMovieCaseUse,
    private val selectMovieUseCase: SelectMovieUseCase
) : ViewModel() {

    private var mVerificado = MutableLiveData<Boolean>()
    val verificado: LiveData<Boolean> = mVerificado

    private val _savedList = MutableLiveData<List<Movie>>()
    val savedList: LiveData<List<Movie>> = _savedList


    fun verificar(id: Int) {
        viewModelScope.launch {
            val valor = withContext(Dispatchers.Default) {
                try {
                    verificarMovieUseCase.invoke(id)
                } catch (ex: Exception) {
                    Log.d("TAG", "addContato: $ex")
                    false
                }
            }
            mVerificado.value = valor
        }
    }

    fun insertMovie(movie: Movie, data: String) {
        viewModelScope.launch {
            try {
                insertMovieUseCase.invoke(movie, data)
                verificar(movie.id)
            } catch (ex: Exception) {
                Log.d("TAG", "insertMovie: $ex")
            }
        }
    }

    fun deleteMovie(id: Int) {
        viewModelScope.launch {
            try {
                deleteMovieUseCase.invoke(id)
                verificar(id)
            } catch (ex: Exception) {
                Log.d("TAG", "insertMovie: $ex")
            }
        }
    }


    fun getSeachMovie(nome: String?) {
        viewModelScope.launch {
            val lista = withContext(Dispatchers.Default) {
                try {
                    selectMovieUseCase.invoke(nome)
                } catch (ex: Exception) {
                    Log.d("TAG", "getSeachMovie: $ex")
                    listOf()
                }
            }
            _savedList.value = lista
        }
    }
}