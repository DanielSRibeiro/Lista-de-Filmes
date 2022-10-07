package com.example.movies.data.network.repository

import com.example.movies.data.network.MovieApi
import com.example.movies.data.network.model.ResultsCategoriesDto
import com.example.movies.data.network.model.categoriesDtoToCategory
import com.example.movies.domain.model.Category
import com.example.movies.domain.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface CategoriesRepository {
    suspend fun getAllCategorias() : Resource<List<Category>>
}
class CategoriesImpl(
    private val movieApi: MovieApi
) : CategoriesRepository {

    override suspend fun getAllCategorias(): Resource<List<Category>> {
        return withContext(Dispatchers.Default){
            val response = movieApi.getAllCategories()
            if(response.isSuccessful){
                Resource.Success(
                    response.body()!!.data.map { it.categoriesDtoToCategory() }
                )
            } else{
                Resource.Fail
            }
        }

    }
}