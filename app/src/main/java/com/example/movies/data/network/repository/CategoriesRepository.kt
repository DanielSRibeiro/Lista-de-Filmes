package com.example.movies.data.network.repository

import com.example.movies.data.network.model.categoriesResponseDtoToCategory
import com.example.movies.data.network.datasource.CategoryRemoteDataSource
import com.example.movies.domain.model.Category
import com.example.movies.domain.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface CategoriesRepository {
    suspend fun getAllCategories(): Resource<List<Category>>
}

class CategoriesImpl(
    private val dataSource: CategoryRemoteDataSource
) : CategoriesRepository {

    override suspend fun getAllCategories(): Resource<List<Category>> {
        val response = dataSource.getAllCategories()
        return if (response is Resource.Success) {
            Resource.Success(response.data.data.map { it.categoriesResponseDtoToCategory() })
        } else {
            response as Resource.Fail
        }
    }
}