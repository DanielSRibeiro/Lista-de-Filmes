package com.example.filmes.presentation.viewmodel.local

import android.util.Log
import androidx.lifecycle.*
import com.example.filmes.domain.usecase.local.SelectMovieUseCase
import com.example.filmes.domain.usecase.local.VerificarMovieUseCase
import com.example.filmes.utilis.TAG_VERIFY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VerificarViewModel(
    private val verificarMovieUseCase: VerificarMovieUseCase
) : ViewModel() {

    private var mVerificado = MutableLiveData<Boolean>()
    val verificado: LiveData<Boolean>
        get() = mVerificado

    fun verificar(id:Int) = CoroutineScope(Dispatchers.Main).launch {
        val valor = withContext(Dispatchers.Default){
            try{
                verificarMovieUseCase.invoke(id)
            }catch (ex:Exception){
                Log.d(TAG_VERIFY, "addContato: $ex")
                false
            }
        }
        mVerificado.value = valor
    }
}