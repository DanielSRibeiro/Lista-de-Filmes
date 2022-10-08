package com.example.movies.domain.usecase.local

import com.example.core.domain.model.Category
import com.example.movies.framework.db.repository.CategoryLocalRepository

interface SaveCategoryUseCase {
    suspend operator fun invoke(category: Category): Long
}

class SaveCategory(
    private val repository: CategoryLocalRepository
) : SaveCategoryUseCase {
    override suspend fun invoke(category: Category): Long = repository.save(category)
}