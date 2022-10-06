package com.example.filmes.presentation.fragment.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.domain.model.CategoriesDto
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.domain.usecase.remote.CategoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(
    private var categoriesUseCase: CategoriesUseCase
) : ViewModel() {
    var nomeCategorias = ""

    private val mCategories = MutableLiveData<String>()
    val categories: LiveData<String> = mCategories

    fun getCategories(movie: MovieDto) {
        viewModelScope.launch {
            val resultsCategories = withContext(Dispatchers.Default) {
                categoriesUseCase.invoke()
            }
            if (resultsCategories.generosFilme.isNotEmpty()) {
                nomeCategorias = ""
                val genresList = resultsCategories.generosFilme
                verificarCategorias(genresList, movie)
                mCategories.value = nomeCategorias

            }

        }
    }

    private fun verificarCategorias(genresList: ArrayList<CategoriesDto>, movie: MovieDto) {
        genresList.forEach { idGenero ->
            movie.generosIds.forEach { idDoFilme ->
                if (idGenero.id == idDoFilme) {
                    nomeCategorias += idGenero.nome + ", "
                }
            }
        }
    }
}