package com.example.movies.framework.remote

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

            if(response.isSuccessful && response.body() != null){
                Resource.Success(response.body()!!)
            }else {
                Resource.Fail
            }

        } catch (e: UnknownHostException) {
            Resource.Fail
        } catch (e: SocketTimeoutException) {
            Resource.Fail
        } catch (e: Exception) {
            Resource.Fail
        }
    }

    override suspend fun getSearchMovies(name: String): Resource<ResultsMoviesResponseDto> {
        return try {
            val response = movieApi.getSearchName(name = name)

            if(response.isSuccessful && response.body() != null){
                Resource.Success(response.body()!!)
            }else {
                Resource.Fail
            }

        } catch (e: UnknownHostException) {
            Resource.Fail
        } catch (e: SocketTimeoutException) {
            Resource.Fail
        } catch (e: Exception) {
            Resource.Fail
        }
    }

}