package com.example.filmes.presentation.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.local.entity.MovieEntity
import com.example.filmes.domain.model.CategoriesDto
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.domain.usecase.local.DeleteMovieCaseUse
import com.example.filmes.domain.usecase.local.InsertMovieUseCase
import com.example.filmes.domain.usecase.local.SelectMovieUseCase
import com.example.filmes.domain.usecase.local.VerificarMovieUseCase
import com.example.filmes.domain.usecase.remote.CategoriesUseCase
import com.example.filmes.utilis.TAG_INSERT
import com.example.filmes.utilis.TAG_SELECT
import com.example.filmes.utilis.TAG_VERIFY
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

    private val mListaSalva = MutableLiveData<List<MovieEntity>>()
    val listaSalva: LiveData<List<MovieEntity>> = mListaSalva


    fun verificar(id: Int) {
        viewModelScope.launch {
            val valor = withContext(Dispatchers.Default) {
                try {
                    verificarMovieUseCase.invoke(id)
                } catch (ex: Exception) {
                    Log.d(TAG_VERIFY, "addContato: $ex")
                    false
                }
            }
            mVerificado.value = valor
        }
    }

    fun insertMovie(movie: MovieDto, data: String) {
        viewModelScope.launch {
            try {
                insertMovieUseCase.invoke(movie, data)
                verificar(movie.id)
            } catch (ex: Exception) {
                Log.d(TAG_INSERT, "insertMovie: $ex")
            }
        }
    }

    fun deleteMovie(id: Int) {
        viewModelScope.launch {
            try {
                deleteMovieUseCase.invoke(id)
                verificar(id)
            } catch (ex: Exception) {
                Log.d(TAG_INSERT, "insertMovie: $ex")
            }
        }
    }


    fun getSeachMovie(nome: String?) {
        viewModelScope.launch {
            val lista = withContext(Dispatchers.Default) {
                try {
                    selectMovieUseCase.invoke(nome)
                } catch (ex: Exception) {
                    Log.d(TAG_SELECT, "getSeachMovie: $ex")
                    listOf()
                }
            }
            mListaSalva.value = lista
        }
    }
}