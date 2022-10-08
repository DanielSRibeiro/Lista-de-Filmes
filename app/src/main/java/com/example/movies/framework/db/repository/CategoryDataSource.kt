package com.example.movies.framework.db.repository

import com.example.movies.framework.db.dao.CategoryDAO
import com.example.movies.framework.db.entity.CategoryEntity
import com.example.movies.framework.db.entity.categoryEntityToCategory
import com.example.core.domain.model.Category

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