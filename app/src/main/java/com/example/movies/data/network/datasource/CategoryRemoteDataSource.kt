package com.example.movies.data.network.datasource

import com.example.movies.data.network.api.MovieApi
import com.example.movies.data.network.model.ResultsCategoriesResponseDto
import com.example.movies.domain.model.Resource
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface CategoryRemoteDataSource {
    suspend fun getAllCategories(): Resource<ResultsCategoriesResponseDto>
}

class CategoryRemoteDataSourceImpl(
    private val movieApi: MovieApi
) : CategoryRemoteDataSource {

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