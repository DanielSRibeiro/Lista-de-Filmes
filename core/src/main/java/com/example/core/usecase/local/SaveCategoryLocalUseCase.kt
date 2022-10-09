package com.example.core.usecase.local

import com.example.core.data.datasource.CategoryLocalRepository
import com.example.core.domain.model.Category

interface SaveCategoryLocalUseCase {
    suspend operator fun invoke(category: Category): Long
}

class SaveCategoryLocalUseCaseImpl(
    private val repository: CategoryLocalRepository
) : SaveCategoryLocalUseCase {
    override suspend fun invoke(category: Category): Long = repository.save(category)
}