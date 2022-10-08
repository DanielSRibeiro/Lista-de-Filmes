package com.example.core.data.repository

import com.example.core.domain.model.Resource

interface CategoriesRepository<T : Any> {
    suspend fun getAllCategories(): Resource<List<T>>
}