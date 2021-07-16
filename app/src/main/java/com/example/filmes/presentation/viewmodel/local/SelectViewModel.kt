package com.example.filmes.presentation.viewmodel.local

import android.util.Log
import androidx.lifecycle.*
import com.example.filmes.data.local.entity.MovieEntity
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.domain.usecase.local.InsertMovieUseCase
import com.example.filmes.domain.usecase.local.SelectMovieUseCase
import com.example.filmes.utilis.TAG_SELECT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SelectViewModel(
    private val selectMovieUseCase: SelectMovieUseCase
) : ViewModel() {

    private val mListaSalva = MutableLiveData<List<MovieEntity>>()

    val listaSalva:LiveData<List<MovieEntity>>
        get() = mListaSalva

    fun getSeachMovie(nome:String?){
        viewModelScope.launch {
            val lista = withContext(Dispatchers.Default){
                try {
                    selectMovieUseCase.invoke(nome)
                }catch (ex:Exception){
                    Log.d(TAG_SELECT, "getSeachMovie: $ex")
                    listOf()
                }
            }
            mListaSalva.value = lista
        }
    }
}