package com.example.movies.domain.usecase.local

import com.example.core.domain.model.Category
import com.example.movies.framework.db.repository.CategoryLocalRepository

interface GetCategoryUseCase {
    suspend operator fun invoke(): List<Category>
}
class GetCategory(
    private val categoryLocalRepository: CategoryLocalRepository
) : GetCategoryUseCase{
    override suspend fun invoke(): List<Category> = categoryLocalRepository.getAll()
}