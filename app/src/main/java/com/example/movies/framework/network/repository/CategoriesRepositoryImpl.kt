package com.example.movies.framework.network.repository

import com.example.core.data.repository.CategoriesRepository
import com.example.core.data.repository.CategoryRemoteDataSource
import com.example.movies.framework.network.response.categoriesResponseDtoToCategory
import com.example.core.domain.model.Category
import com.example.core.domain.model.Resource
import com.example.movies.framework.network.response.ResultsCategoriesResponseDto

class CategoriesRepositoryImpl(
    private val dataSource: CategoryRemoteDataSource<ResultsCategoriesResponseDto>
) : CategoriesRepository<Category> {

    override suspend fun getAllCategories(): Resource<List<Category>> {
        val response = dataSource.getAllCategories()
        return if (response is Resource.Success) {
            Resource.Success(response.data.data.map { it.categoriesResponseDtoToCategory() })
        } else {
            response as Resource.Fail
        }
    }
}