package com.example.core.data.repository

import com.example.core.domain.model.Resource

interface MovieRepository<T : Any> {
    suspend fun getAllPopulars(): Resource<List<T>>
    suspend fun getSearchMovies(name: String): Resource<List<T>>
}