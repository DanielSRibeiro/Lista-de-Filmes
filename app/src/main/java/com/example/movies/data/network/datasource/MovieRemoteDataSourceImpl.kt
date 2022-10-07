package com.example.movies.data.network.datasource

import com.example.movies.data.network.MovieApi
import com.example.movies.data.network.model.ResultsMoviesResponseDto
import com.example.movies.domain.model.Resource
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface MovieRemoteDataSource {
    suspend fun getAllPopulars(): Resource<ResultsMoviesResponseDto>
    suspend fun getSearchMovies(name: String): Resource<ResultsMoviesResponseDto>
}

class MovieRemoteDataSourceImpl(
    private val movieApi: MovieApi
) : MovieRemoteDataSource {

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