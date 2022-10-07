package com.example.movies.domain.usecase.local

import com.example.movies.data.database.repository.CategoryLocalRepository
import com.example.movies.domain.model.Category

interface SaveCategoryLocalUseCase {
    suspend operator fun invoke(category: Category): Long
}

class SaveCategoryLocal(
    private val repository: CategoryLocalRepository
) : SaveCategoryLocalUseCase {
    override suspend fun invoke(category: Category): Long = repository.save(category)
}