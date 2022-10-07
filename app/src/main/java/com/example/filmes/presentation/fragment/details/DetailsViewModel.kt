package com.example.filmes.presentation.fragment.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.network.model.CategoriesDto
import com.example.filmes.data.network.model.MovieDto
import com.example.filmes.domain.usecase.remote.CategoriesUseCase
import kotlinx.coroutines.launch

class DetailsViewModel(
    private var categoriesUseCase: CategoriesUseCase
) : ViewModel() {
    var nomeCategorias = ""

    private val mCategories = MutableLiveData<String>()
    val categories: LiveData<String> = mCategories

    fun getCategories(movie: MovieDto) {
        viewModelScope.launch {
            val resultsCategories = categoriesUseCase.invoke()
            if (resultsCategories.data.isNotEmpty()) {
                nomeCategorias = ""
                val genresList = resultsCategories.data
                verificarCategorias(genresList, movie)
                mCategories.value = nomeCategorias

            }

        }
    }

    private fun verificarCategorias(genresList: ArrayList<CategoriesDto>, movie: MovieDto) {
        genresList.forEach { idGenero ->
            movie.genreIds.forEach { idDoFilme ->
                if (idGenero.id == idDoFilme) {
                    nomeCategorias += idGenero.nome + ", "
                }
            }
        }
    }
}