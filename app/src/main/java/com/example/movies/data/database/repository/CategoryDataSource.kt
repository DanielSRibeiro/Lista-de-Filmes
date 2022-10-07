package com.example.movies.data.database.repository

import com.example.movies.data.database.dao.CategoryDAO
import com.example.movies.data.database.dao.MovieDAO
import com.example.movies.data.database.entity.CategoryEntity
import com.example.movies.data.database.entity.MovieEntity
import com.example.movies.data.database.entity.categoryEntityToCategory
import com.example.movies.domain.model.Category

interface CategoryLocalRepository {
    suspend fun save(category: Category):Long
    suspend fun getAll(): List<Category>
}

class CategoryDataSource(
    private var dao: CategoryDAO
) : CategoryLocalRepository {
    override suspend fun save(category: Category): Long {
        val entity = CategoryEntity (
            id = category.id.toLong(),
            name = category.name
        )
        return dao.save(entity)
    }

    override suspend fun getAll(): List<Category> {
        return dao.getAll().map { it.categoryEntityToCategory() }
    }
}