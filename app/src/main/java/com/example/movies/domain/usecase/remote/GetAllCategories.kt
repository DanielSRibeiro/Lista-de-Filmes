package com.example.movies.domain.usecase.remote

import com.example.core.data.repository.CategoriesRepository
import com.example.core.domain.model.Category
import com.example.core.domain.model.Resource

interface GetAllCategoriesUseCase{
    suspend operator fun invoke(): Resource<List<Category>>
}
class GetAllCategories(
    private var categoriesRepository: CategoriesRepository<Category>
):GetAllCategoriesUseCase {

    override suspend operator fun invoke(): Resource<List<Category>> = categoriesRepository.getAllCategories()
}