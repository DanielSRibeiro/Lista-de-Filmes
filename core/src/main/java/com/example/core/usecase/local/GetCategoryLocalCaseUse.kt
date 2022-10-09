package com.example.core.usecase.local

import com.example.core.data.datasource.CategoryLocalRepository
import com.example.core.domain.model.Category

interface GetCategoryUseCase {
    suspend operator fun invoke(): List<Category>
}
class GetCategoryLocalCaseUseImpl(
    private val categoryLocalRepository: CategoryLocalRepository
) : GetCategoryUseCase {
    override suspend fun invoke(): List<Category> = categoryLocalRepository.getAll()
}