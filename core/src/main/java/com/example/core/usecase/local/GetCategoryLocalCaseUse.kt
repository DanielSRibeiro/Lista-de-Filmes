package com.example.core.usecase.local

import com.example.core.data.repository.CategoryLocalRepository
import com.example.core.domain.model.Category

interface GetCategoryLocalUseCase {
    suspend operator fun invoke(): List<Category>
}
class GetCategoryLocalUseCaseImpl(
    private val categoryLocalRepository: CategoryLocalRepository
) : GetCategoryLocalUseCase {
    override suspend fun invoke(): List<Category> = categoryLocalRepository.getAll()
}