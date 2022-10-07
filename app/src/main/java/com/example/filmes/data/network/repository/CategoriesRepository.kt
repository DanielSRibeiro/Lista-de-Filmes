package com.example.filmes.data.network.repository

import com.example.filmes.data.network.MovieApi
import com.example.filmes.data.network.model.ResultsCategoriesDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface CategoriesRepository {
    suspend fun getAllCategorias() : ResultsCategoriesDto
}

class CategoriesImpl(
    private val movieApi: MovieApi
) : CategoriesRepository {

    override suspend fun getAllCategorias(): ResultsCategoriesDto {
        return withContext(Dispatchers.Default){
            val response = movieApi.getAllCategories()
            if(response.isSuccessful){
                response.body()!!
            } else{
                ResultsCategoriesDto(arrayListOf())
            }
        }

    }
}