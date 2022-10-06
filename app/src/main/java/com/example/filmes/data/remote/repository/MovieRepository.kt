package com.example.filmes.data.remote.repository

import android.util.Log
import com.example.filmes.data.remote.ApiService
import com.example.filmes.utilis.TAG_MOVIE
import com.example.filmes.domain.model.ResultsMoviesDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class MovieImpl(val apiService: ApiService) : MovieRepository {

    override suspend fun getAllPopulars(): ResultsMoviesDto? {
        return withContext(Dispatchers.Default) {
            var response = apiService.getAllPopularMovies()
            verificarResponse(response)
        }

    }

    override suspend fun getSearchMovies(name: String): ResultsMoviesDto? {
        return withContext(Dispatchers.Default) {
            var response = apiService.getSearchName(name = name)
            verificarResponse(response)
        }
    }

    fun verificarResponse(response: Response<ResultsMoviesDto>): ResultsMoviesDto? {
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            Log.d(TAG_MOVIE, "Error: ${response.errorBody()} ")
            Log.d(TAG_MOVIE, "Message: ${response.message()} ")
            Log.d(TAG_MOVIE, "Code: ${response.code()} ")
            null
        }
    }
}

interface MovieRepository {
    suspend fun getAllPopulars(): ResultsMoviesDto?
    suspend fun getSearchMovies(name: String): ResultsMoviesDto?
}