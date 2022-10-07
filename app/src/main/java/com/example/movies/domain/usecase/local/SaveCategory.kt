package com.example.movies.domain.usecase.local

import com.example.movies.data.database.repository.CategoryLocalRepository
import com.example.movies.domain.model.Category

interface SaveCategoryUseCase {
    suspend operator fun invoke(category: Category): Long
}

class SaveCategory(
    private val repository: CategoryLocalRepository
) : SaveCategoryUseCase {
    override suspend fun invoke(category: Category): Long = repository.save(category)
}