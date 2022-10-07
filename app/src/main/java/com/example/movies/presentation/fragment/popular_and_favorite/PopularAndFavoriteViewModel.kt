package com.example.movies.presentation.fragment.popular_and_favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.model.Category
import com.example.movies.domain.model.Resource
import com.example.movies.domain.usecase.local.SaveCategoryLocalUseCase
import com.example.movies.domain.usecase.remote.CategoriesUseCase
import kotlinx.coroutines.launch

class PopularAndFavoriteViewModel(
    private val useCase: SaveCategoryLocalUseCase,
    private var categoriesUseCase: CategoriesUseCase
) : ViewModel() {

    fun getCategories() {
        viewModelScope.launch {
            val resource = categoriesUseCase.invoke()

            if (resource is Resource.Success) {
                insertCategory(resource.data)
            }
        }
    }

    private fun insertCategory(categories: List<Category>) {
        viewModelScope.launch {
            categories.forEach {
                try {
                    val id = useCase.invoke(it)
                    Log.d("TAG", "insertMovie: $id")
                } catch (ex: Exception) {
                    Log.d("TAG", "insertMovie: $ex")
                }
            }
        }
    }
}