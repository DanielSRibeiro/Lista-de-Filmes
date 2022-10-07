package com.example.filmes.domain.usecase.remote

import android.util.Log
import com.example.filmes.data.network.repository.CategoriesRepository
import com.example.filmes.data.network.model.ResultsCategoriesDto

class GetCategories(
    private var categoriesRepository: CategoriesRepository
):CategoriesUseCase {

    override suspend operator fun invoke(): ResultsCategoriesDto = try{
        categoriesRepository.getAllCategorias()
    }catch (ex:Exception){
        Log.d("TAG", "categoriesUseCase: $ex")
        ResultsCategoriesDto(arrayListOf())
    }
}

interface CategoriesUseCase{
    suspend operator fun invoke(): ResultsCategoriesDto
}