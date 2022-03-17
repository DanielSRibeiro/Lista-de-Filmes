package com.example.filmes.data.remote.network

import com.example.filmes.utilis.LANGUAGE
import com.example.filmes.utilis.USER_KEY
import com.example.filmes.domain.model.ResultsCategoriesDto
import com.example.filmes.domain.model.ResultsMoviesDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getAllPopularMovies(
        @Query("api_key") keyApi:String = USER_KEY,
        @Query("language") language:String = LANGUAGE
    ) : Response<ResultsMoviesDto>

    @GET("search/movie")
    suspend fun getSearchName(
        @Query("api_key") keyApi:String = USER_KEY,
        @Query("language") language:String = LANGUAGE,
        @Query("query") name:String
    ) : Response<ResultsMoviesDto>

    @GET("genre/movie/list")
    suspend fun getAllCategories(
        @Query("api_key") keyApi:String = USER_KEY,
        @Query("language") language:String = LANGUAGE
    ) : Response<ResultsCategoriesDto>
}