package com.example.filmes.presentation.viewmodel.local

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.R
import com.example.filmes.domain.usecase.local.DeleteMovieCaseUse
import com.example.filmes.utilis.TAG_INSERT
import kotlinx.coroutines.launch

class DeleteViewModel(
    private val deleteMovieUseCase: DeleteMovieCaseUse
) : ViewModel() {

    fun deleteMovie(id: Int){
        viewModelScope.launch {
            try{
                deleteMovieUseCase.invoke(id)
            }catch (ex:Exception){
                Log.d(TAG_INSERT, "insertMovie: $ex")
            }
        }
    }
}