package com.example.filmes.data.repository

import android.util.Log
import com.example.filmes.data.api.RetrofitTask
import com.example.filmes.domain.model.ResultsMoviesDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class MovieImplementation(val retrofitTask: RetrofitTask) : MovieRepository {

    private val TAG = "MovieImplementation"

    override suspend fun getAllPopulars(): ResultsMoviesDto {
        return withContext(Dispatchers.Default){
                var response = retrofitTask.getRetrofitTask().getAllPopularMovies()
            verificarResponse(response)
        }

    }

    override suspend fun getSearchMovies(name:String): ResultsMoviesDto {
        return withContext(Dispatchers.Default){
                var response = retrofitTask.getRetrofitTask().getSearchName(
                    retrofitTask.api_key,
                    retrofitTask.language,
                    name)
                verificarResponse(response)
        }
    }

    fun verificarResponse(response : Response<ResultsMoviesDto>) : ResultsMoviesDto{
        return if(response.isSuccessful){
                response.body()!!
        }else{
            Log.d(TAG, "Error: ${response.errorBody()} ")
            Log.d(TAG, "Code: ${response.code()} ")
            ResultsMoviesDto(arrayListOf(), Integer(0), Integer(0))
        }
    }
}

interface MovieRepository {
    suspend fun getAllPopulars() : ResultsMoviesDto

    suspend fun getSearchMovies(name:String) : ResultsMoviesDto
}