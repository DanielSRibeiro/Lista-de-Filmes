package com.example.core.data.datasource

import com.example.core.domain.model.Category

interface CategoryLocalRepository {
    suspend fun save(category: Category):Long
    suspend fun getAll(): List<Category>
}