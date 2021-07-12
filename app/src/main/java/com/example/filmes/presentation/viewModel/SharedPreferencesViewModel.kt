package com.example.filmes.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.utils.SharedPreferecesConfig

class SharedPreferencesViewModel(var preferecesConfig: SharedPreferecesConfig) :ViewModel(){

    var mutableVerificarFavorito = MutableLiveData<Boolean>()
    var mutableAllFilmesSalvos = MutableLiveData<ArrayList<MovieDto>>()

    val liveVerificarFavorito:LiveData<Boolean>
        get() = mutableVerificarFavorito

    val liveAllFilmesSalvos:LiveData<ArrayList<MovieDto>>
        get() = mutableAllFilmesSalvos

    fun inserirListFavorito(movie: MovieDto, listFilmesSalvo:ArrayList<MovieDto>){
        var jaEstaSalvo = false
        var posicaoList:Int = -1
        for(posicao in listFilmesSalvo.indices){
            if (movie.id == listFilmesSalvo[posicao].id){
                jaEstaSalvo = true
                posicaoList = posicao
            }
        }
        if(jaEstaSalvo)
            listFilmesSalvo.removeAt(posicaoList)
        else
            listFilmesSalvo.add(movie)

        preferecesConfig.setListaSalva(listFilmesSalvo)
        getListaSalva()
    }

    fun verificarFavorito(movie: MovieDto, listFilmesSalvo:ArrayList<MovieDto>){
        var foiSalvo = false
        for(posicao in listFilmesSalvo.indices){
            if (movie.id == listFilmesSalvo[posicao].id){
                foiSalvo = true
            }
        }
        mutableVerificarFavorito.value = foiSalvo
        preferecesConfig.setListaSalva(listFilmesSalvo)
    }

    fun getListaSalva(){
        mutableAllFilmesSalvos.value = preferecesConfig.getListaSalva()
    }


    class ViewModelFactory(var preferecesConfig: SharedPreferecesConfig) :ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(SharedPreferecesConfig::class.java)
                .newInstance(preferecesConfig)
        }
    }
}