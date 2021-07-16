package com.example.filmes.presentation.viewmodel.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.domain.model.CategoriesDto
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.domain.usecase.remote.CategoriesUseCase
import com.example.filmes.domain.usecase.remote.GetCategories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoriesViewModel(private var categoriesUseCase: CategoriesUseCase):ViewModel() {

    private val mCategories = MutableLiveData<String>()
    var nomeCategorias = ""

    val categories:LiveData<String>
        get() = mCategories

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
                if(idGenero.id.equals(idDoFilme)){
                    nomeCategorias += idGenero.nome+", "
                }
            }
        }
    }
}