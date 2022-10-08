package com.example.movies.framework.network.datasource

import com.example.core.data.repository.CategoryRemoteDataSource
import com.example.movies.framework.network.api.MovieApi
import com.example.movies.framework.network.model.ResultsCategoriesResponseDto
import com.example.core.domain.model.Resource
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CategoryRemoteDataSourceImpl(
    private val movieApi: MovieApi
) : CategoryRemoteDataSource<ResultsCategoriesResponseDto> {

    override suspend fun getAllCategories(): Resource<ResultsCategoriesResponseDto> {
        return try {
            val response = movieApi.getAllCategories()

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