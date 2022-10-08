package com.example.movies.domain.usecase.local

import com.example.movies.framework.database.repository.CategoryLocalRepository
import com.example.movies.framework.database.repository.MovieLocalRepository
import com.example.movies.domain.model.Category
import com.example.movies.domain.model.Movie

interface GetCategoryUseCase {
    suspend operator fun invoke(): List<Category>
}
class GetCategory(
    private val categoryLocalRepository: CategoryLocalRepository
) : GetCategoryUseCase{
    override suspend fun invoke(): List<Category> = categoryLocalRepository.getAll()
}