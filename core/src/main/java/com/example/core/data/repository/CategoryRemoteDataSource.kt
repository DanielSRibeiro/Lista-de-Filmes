package com.example.core.data.repository

import com.example.core.domain.model.Resource

interface CategoryRemoteDataSource<T : Any> {
    suspend fun getAllCategories(): Resource<T>
}