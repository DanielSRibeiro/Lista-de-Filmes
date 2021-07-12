package com.example.filmes.domain.usecase

import android.util.Log
import com.example.filmes.data.repository.CategoriesRepository
import com.example.filmes.domain.model.ResultsCategoriesDto

class CategoriesUseCase(private var categoriesDatabase: CategoriesRepository) {

    private val TAG = "CategoriesUseCase"

    suspend operator fun invoke():ResultsCategoriesDto = try{
        categoriesDatabase.getAllCategorias()
    }catch (ex:Exception){
        Log.d(TAG, "Error: $ex")
        ResultsCategoriesDto(arrayListOf())
    }
}