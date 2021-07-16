package com.example.filmes.presentation.viewmodel.local

import android.util.Log
import androidx.lifecycle.*
import com.example.filmes.R
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.domain.usecase.local.InsertMovieUseCase
import com.example.filmes.utilis.TAG_INSERT
import kotlinx.coroutines.launch

class InsertViewModel(
    private val insertMovieUseCase: InsertMovieUseCase
) :ViewModel(){

    fun insertMovie(movie: MovieDto, data:String){
        viewModelScope.launch {
            try{
                insertMovieUseCase.invoke(movie, data)
            }catch (ex:Exception){
                Log.d(TAG_INSERT, "insertMovie: $ex")
            }

        }
    }
}