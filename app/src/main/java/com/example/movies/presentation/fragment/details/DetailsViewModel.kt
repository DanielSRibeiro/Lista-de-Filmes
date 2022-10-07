package com.example.movies.presentation.fragment.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.network.model.MovieResponseDto
import com.example.movies.domain.model.Category
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Resource
import com.example.movies.domain.usecase.local.GetCategoryUseCase
import com.example.movies.domain.usecase.remote.CategoriesUseCase
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getCategoryUseCase: GetCategoryUseCase
) : ViewModel() {
    var nomeCategorias = ""

    private val mCategories = MutableLiveData<String>()
    val categories: LiveData<String> = mCategories

    fun getCategories(movie: Movie) {
        viewModelScope.launch {
            val categoriesList = getCategoryUseCase.invoke()

            categoriesList.forEach { category ->
                movie.genreIds.forEach {
                    if (category.id == it) {
                        nomeCategorias += category.name + ", "
                    }
                }
            }
            mCategories.value = nomeCategorias
        }
    }
}