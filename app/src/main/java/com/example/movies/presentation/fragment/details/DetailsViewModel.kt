package com.example.movies.presentation.fragment.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.network.model.MovieResponseDto
import com.example.movies.domain.model.Category
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Resource
import com.example.movies.domain.usecase.remote.CategoriesUseCase
import kotlinx.coroutines.launch

class DetailsViewModel(
    private var categoriesUseCase: CategoriesUseCase
) : ViewModel() {
    var nomeCategorias = ""

    private val mCategories = MutableLiveData<String>()
    val categories: LiveData<String> = mCategories

    fun getCategories(movie: Movie) {
        viewModelScope.launch {
            val resource = categoriesUseCase.invoke()

            if (resource is Resource.Success) {
                nomeCategorias = ""
                val genresList = resource.data
                verificarCategorias(genresList, movie)
                mCategories.value = nomeCategorias
            }
        }
    }

    private fun verificarCategorias(genresList: List<Category>, movie: Movie) {
        genresList.forEach { idGenero ->
            movie.genreIds.forEach { idDoFilme ->
                if (idGenero.id == idDoFilme) {
                    nomeCategorias += idGenero.name + ", "
                }
            }
        }
    }
}