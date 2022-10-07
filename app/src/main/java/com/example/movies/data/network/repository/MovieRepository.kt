package com.example.movies.data.network.repository

import com.example.movies.data.network.MovieApi
import com.example.movies.data.network.model.ResultsMoviesDto
import retrofit2.Response

interface MovieRepository {
    suspend fun getAllPopulars(): ResultsMoviesDto?
    suspend fun getSearchMovies(name: String): ResultsMoviesDto?
}

class MovieImpl(
    private val movieApi: MovieApi
) : MovieRepository {

    override suspend fun getAllPopulars(): ResultsMoviesDto? {
        val response = movieApi.getAllPopularMovies()
        return verificarResponse(response)
    }

    override suspend fun getSearchMovies(name: String): ResultsMoviesDto? {
        val response = movieApi.getSearchName(name = name)
        return verificarResponse(response)
    }

    fun verificarResponse(response: Response<ResultsMoviesDto>): ResultsMoviesDto? {
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            null
        }
    }
}