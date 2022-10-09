package com.example.core.usecase.remote

import com.example.core.data.repository.CategoriesRepository
import com.example.core.domain.model.Category
import com.example.core.domain.model.Resource

interface GetAllCategoriesUseCase{
    suspend operator fun invoke(): Resource<List<Category>>
}
class GetAllCategoriesUseCaseImpl(
    private var categoriesRepository: CategoriesRepository<Category>
): GetAllCategoriesUseCase {

    override suspend operator fun invoke(): Resource<List<Category>> = categoriesRepository.getAllCategories()
}