package com.example.movies.domain.usecase.remote

import com.example.movies.data.network.repository.CategoriesRepository
import com.example.movies.domain.model.Category
import com.example.movies.domain.model.Resource

interface GetAllCategoriesUseCase{
    suspend operator fun invoke(): Resource<List<Category>>
}
class GetAllCategories(
    private var categoriesRepository: CategoriesRepository
):GetAllCategoriesUseCase {

    override suspend operator fun invoke(): Resource<List<Category>> = categoriesRepository.getAllCategories()
}