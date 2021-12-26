package com.example.filmes.presentation.view.details

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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModelLocal(
    private val insertMovieUseCase: InsertMovieUseCase,
    private val verificarMovieUseCase: VerificarMovieUseCase,
    private val deleteMovieUseCase: DeleteMovieCaseUse,
    private var categoriesUseCase: CategoriesUseCase,
    private val selectMovieUseCase: SelectMovieUseCase
) : ViewModel() {

    var nomeCategorias = ""

    private val mCategories = MutableLiveData<String>()
    val categories:LiveData<String>
        get() = mCategories

    private var mVerificado = MutableLiveData<Boolean>()
    val verificado: LiveData<Boolean>
        get() = mVerificado

    private val mListaSalva = MutableLiveData<List<MovieEntity>>()
    val listaSalva:LiveData<List<MovieEntity>>
        get() = mListaSalva


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

    fun insertMovie(movie: MovieDto, data:String){
        viewModelScope.launch {
            try{
                insertMovieUseCase.invoke(movie, data)
                verificar(movie.id)
            }catch (ex:Exception){
                Log.d(TAG_INSERT, "insertMovie: $ex")
            }
        }
    }

    fun deleteMovie(id: Int){
        viewModelScope.launch {
            try{
                deleteMovieUseCase.invoke(id)
                verificar(id)
            }catch (ex:Exception){
                Log.d(TAG_INSERT, "insertMovie: $ex")
            }
        }
    }

    fun getCategories(movie:MovieDto){
        viewModelScope.launch{
            var resultsCategories = withContext(Dispatchers.Default){
                categoriesUseCase.invoke()
            }
            if(!resultsCategories.generosFilme.isNullOrEmpty()){
                nomeCategorias = ""
                var genresList = resultsCategories.generosFilme
                verificarCategorias(genresList, movie)
                mCategories.value = nomeCategorias

            }

        }
    }

    private fun verificarCategorias(genresList:ArrayList<CategoriesDto>, movie: MovieDto) {
        //verifica os gêneros e acrecenta na variavel nomeCategorias
        genresList.forEach { idGenero ->
            movie.generosIds.forEach { idDoFilme ->
                if(idGenero.id == idDoFilme){
                    nomeCategorias += idGenero.nome+", "
                }
            }
        }
    }

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