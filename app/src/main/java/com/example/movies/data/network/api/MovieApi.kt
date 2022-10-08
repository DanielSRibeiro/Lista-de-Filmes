package com.example.movies.data.network.api

import com.example.movies.data.network.model.ResultsCategoriesResponseDto
import com.example.movies.data.network.model.ResultsMoviesResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getAllPopularMovies(
        @Query("page") page:Int = 1,
    ) : Response<ResultsMoviesResponseDto>

    @GET("search/movie")
    suspend fun getSearchName(
        @Query("page") page:Int = 1,
        @Query("query") name:String
    ) : Response<ResultsMoviesResponseDto>

    @GET("genre/movie/list")
    suspend fun getAllCategories() : Response<ResultsCategoriesResponseDto>
}