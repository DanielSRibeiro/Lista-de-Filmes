package com.example.movies.framework.network.repository

import com.example.core.data.repository.MovieRemoteDataSource
import com.example.core.data.repository.MovieRepository
import com.example.movies.framework.network.response.movieResponseDtoToMovie
import com.example.core.domain.model.Movie
import com.example.core.domain.model.Resource
import com.example.movies.framework.network.response.ResultsMoviesResponseDto

class MovieRepositoryImpl(
    private val dataSource: MovieRemoteDataSource<ResultsMoviesResponseDto>
) : MovieRepository<Movie> {

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