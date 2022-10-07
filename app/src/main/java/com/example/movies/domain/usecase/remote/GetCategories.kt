package com.example.movies.domain.usecase.remote

import android.util.Log
import com.example.movies.data.network.repository.CategoriesRepository
import com.example.movies.domain.model.Category
import com.example.movies.domain.model.Resource


interface CategoriesUseCase{
    suspend operator fun invoke(): Resource<List<Category>>
}
class GetCategories(
    private var categoriesRepository: CategoriesRepository
):CategoriesUseCase {

    override suspend operator fun invoke(): Resource<List<Category>> = try{
        categoriesRepository.getAllCategorias()
    }catch (ex:Exception){
        Log.d("TAG", "categoriesUseCase: $ex")
        Resource.Fail
    }
}