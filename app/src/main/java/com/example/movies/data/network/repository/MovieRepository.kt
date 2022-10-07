package com.example.movies.data.network.repository

import com.example.movies.data.network.MovieApi
import com.example.movies.data.network.model.ResultsMoviesResponseDto
import com.example.movies.data.network.model.movieResponseDtoToMovie
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Resource
import retrofit2.Response

interface MovieRepository {
    suspend fun getAllPopulars(): Resource<List<Movie>>
    suspend fun getSearchMovies(name: String): Resource<List<Movie>>
}

class MovieImpl(
    private val movieApi: MovieApi
) : MovieRepository {

    override suspend fun getAllPopulars(): Resource<List<Movie>> {
        val response = movieApi.getAllPopularMovies()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!.data.map { it.movieResponseDtoToMovie() })
        } else {
            Resource.Fail
        }
    }

    override suspend fun getSearchMovies(name: String): Resource<List<Movie>> {
        val response = movieApi.getSearchName(name = name)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!.data.map { it.movieResponseDtoToMovie() })
        } else {
            Resource.Fail
        }
    }
}