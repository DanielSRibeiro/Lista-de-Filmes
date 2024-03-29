package com.example.movies.presentation.popular_and_favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.Category
import com.example.core.domain.model.Resource
import com.example.core.usecase.local.SaveCategoryLocalUseCase
import com.example.core.usecase.remote.GetAllCategoriesUseCase
import kotlinx.coroutines.launch

class PopularAndFavoriteViewModel(
    private val saveCategory: SaveCategoryLocalUseCase,
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
                val id = saveCategory.invoke(it)
                Log.d("TAG", "insertMovie: $id")
            }
        }
    }
}