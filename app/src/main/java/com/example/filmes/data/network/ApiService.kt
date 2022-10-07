package com.example.filmes.data.network

import com.example.filmes.domain.model.ResultsCategoriesDto
import com.example.filmes.domain.model.ResultsMoviesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getAllPopularMovies(
        @Query("page") page:Int = 1,
    ) : Response<ResultsMoviesDto>

    @GET("search/movie")
    suspend fun getSearchName(
        @Query("page") page:Int = 1,
        @Query("query") name:String
    ) : Response<ResultsMoviesDto>

    @GET("genre/movie/list")
    suspend fun getAllCategories() : Response<ResultsCategoriesDto>
}