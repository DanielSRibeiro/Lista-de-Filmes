package com.example.core.data.datasource

import com.example.core.domain.model.Resource

interface MovieRemoteDataSource<T : Any> {
    suspend fun getAllPopulars(): Resource<T>
    suspend fun getSearchMovies(name: String): Resource<T>
}