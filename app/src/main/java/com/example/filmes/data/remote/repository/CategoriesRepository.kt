package com.example.filmes.data.remote.repository

import android.util.Log
import com.example.filmes.data.remote.ApiService
import com.example.filmes.utilis.TAG_CATEGORIES
import com.example.filmes.domain.model.ResultsCategoriesDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoriesImpl(val apiService: ApiService) : CategoriesRepository {

    override suspend fun getAllCategorias(): ResultsCategoriesDto {
        return withContext(Dispatchers.Default){
            val response = apiService.getAllCategories()
            if(response.isSuccessful){
                response.body()!!
            } else{
                Log.d(TAG_CATEGORIES, "Error: ${response.errorBody()} ")
                Log.d(TAG_CATEGORIES, "Code: ${response.code()} ")
                ResultsCategoriesDto(arrayListOf())
            }
        }

    }

}
interface CategoriesRepository {
    suspend fun getAllCategorias() : ResultsCategoriesDto
}