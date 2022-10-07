package com.example.movies.presentation.popular_and_favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.model.Category
import com.example.movies.domain.model.Resource
import com.example.movies.domain.usecase.local.SaveCategoryUseCase
import com.example.movies.domain.usecase.remote.GetAllCategoriesUseCase
import kotlinx.coroutines.launch

class PopularAndFavoriteViewModel(
    private val saveCategory: SaveCategoryUseCase,
    private var getAllCategories: GetAllCategoriesUseCase
) : ViewModel() {

    fun getAllCategories() {
        viewModelScope.launch {
            val resource = getAllCategories.invoke()

            if (resource is Resource.Success) {
                insertCategories(resource.data)
            } else {
                Log.d("TAG", "Error")
            }
        }
    }

    private fun insertCategories(categories: List<Category>) {
        viewModelScope.launch {
            categories.forEach {
                try {
                    val id = saveCategory.invoke(it)
                    Log.d("TAG", "insertMovie: $id")
                } catch (ex: Exception) {
                    Log.d("TAG", "insertMovie: $ex")
                }
            }
        }
    }
}