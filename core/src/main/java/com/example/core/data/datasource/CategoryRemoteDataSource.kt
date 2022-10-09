package com.example.core.data.datasource

import com.example.core.domain.model.Resource

interface CategoryRemoteDataSource<T : Any> {
    suspend fun getAllCategories(): Resource<T>
}