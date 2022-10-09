package com.example.movies.framework.remote

import com.example.core.ErrorStates
import com.example.core.data.datasource.CategoryRemoteDataSource
import com.example.movies.framework.network.MovieApi
import com.example.movies.framework.network.response.ResultsCategoriesResponseDto
import com.example.core.domain.model.Resource
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CategoryRemoteDataSourceImpl(
    private val movieApi: MovieApi
) : CategoryRemoteDataSource<ResultsCategoriesResponseDto> {

    override suspend fun getAllCategories(): Resource<ResultsCategoriesResponseDto> {
        return try {
            val response = movieApi.getAllCategories()

            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                var message = response.code().toString()

                if(response.body() != null){
                    message += "- ${response.body()}"
                }
                if(response.message().isNotBlank()){
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