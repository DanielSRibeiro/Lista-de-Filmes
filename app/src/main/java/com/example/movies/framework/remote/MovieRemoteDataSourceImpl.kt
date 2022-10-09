package com.example.movies.framework.remote

import com.example.core.ErrorStates
import com.example.core.data.datasource.MovieRemoteDataSource
import com.example.movies.framework.network.MovieApi
import com.example.movies.framework.network.response.ResultsMoviesResponseDto
import com.example.core.domain.model.Resource
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MovieRemoteDataSourceImpl(
    private val movieApi: MovieApi
) : MovieRemoteDataSource<ResultsMoviesResponseDto> {

    override suspend fun getAllPopulars(): Resource<ResultsMoviesResponseDto> {
        return try {
            val response = movieApi.getAllPopularMovies()

            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                var message = response.code().toString()

                if (response.body() != null) {
                    message += "- ${response.body()}"
                }
                if (response.message().isNotBlank()) {
                    message += "- ${response.message()}"
                }

                Resource.Fail(ErrorStates.Fail, message)
            }

        } catch (e: UnknownHostException) {
            Resource.Fail(ErrorStates.NoInternet, e.message ?: e.toString())
        } catch (e: SocketTimeoutException) {
            Resource.Fail(ErrorStates.TimeOut, e.message ?: e.toString())
        } catch (e: Exception) {
            Resource.Fail(ErrorStates.Exception, e.message ?: e.toString())
        }
    }

    override suspend fun getSearchMovies(name: String): Resource<ResultsMoviesResponseDto> {
        return try {
            val response = movieApi.getSearchName(name = name)

            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                var message = response.code().toString()

                if (response.body() != null) {
                    message += "- ${response.body()}"
                }
                if (response.message().isNotBlank()) {
                    message += "- ${response.message()}"
                }

                Resource.Fail(ErrorStates.Fail, message)
            }

        } catch (e: UnknownHostException) {
            Resource.Fail(ErrorStates.NoInternet, e.message ?: e.toString())
        } catch (e: SocketTimeoutException) {
            Resource.Fail(ErrorStates.TimeOut, e.message ?: e.toString())
        } catch (e: Exception) {
            Resource.Fail(ErrorStates.Exception, e.message ?: e.toString())
        }
    }

}