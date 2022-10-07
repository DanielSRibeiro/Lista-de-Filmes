package com.example.movies.data.network.repository

import com.example.movies.data.network.model.movieResponseDtoToMovie
import com.example.movies.data.network.datasource.MovieRemoteDataSource
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Resource

interface MovieRepository {
    suspend fun getAllPopulars(): Resource<List<Movie>>
    suspend fun getSearchMovies(name: String): Resource<List<Movie>>
}

class MovieRepositoryImpl(
    private val dataSource: MovieRemoteDataSource
) : MovieRepository {

    override suspend fun getAllPopulars(): Resource<List<Movie>> {
        val response = dataSource.getAllPopulars()
        return if (response is Resource.Success) {
            Resource.Success(response.data.data.map { it.movieResponseDtoToMovie()  })
        } else {
            response as Resource.Fail
        }
    }

    override suspend fun getSearchMovies(name: String): Resource<List<Movie>> {
        val response = dataSource.getSearchMovies(name = name)
        return if (response is Resource.Success) {
            Resource.Success(response.data.data.map { it.movieResponseDtoToMovie()  })
        } else {
            response as Resource.Fail
        }
    }
}