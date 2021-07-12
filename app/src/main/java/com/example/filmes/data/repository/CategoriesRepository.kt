package com.example.filmes.data.repository

import android.icu.util.Output
import android.util.Log
import com.example.filmes.data.api.RetrofitTask
import com.example.filmes.domain.model.CategoriesDto
import com.example.filmes.domain.model.ResultsCategoriesDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoriesImplementation(val retrofitTask: RetrofitTask) : CategoriesRepository {

    private val TAG = "CategoriesRepository"

    override suspend fun getAllCategorias(): ResultsCategoriesDto {
        return withContext(Dispatchers.Default){
            var response = retrofitTask.getRetrofitTask().getAllCategories(
                retrofitTask.api_key,
                retrofitTask.language
            )

            if(response.isSuccessful)
                response.body()!!
            else
                ResultsCategoriesDto(arrayListOf())
        }

    }

}
interface CategoriesRepository {
    suspend fun getAllCategorias() : ResultsCategoriesDto
}