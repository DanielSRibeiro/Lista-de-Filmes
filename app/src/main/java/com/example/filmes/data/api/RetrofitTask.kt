package com.example.filmes.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitTask {

    companion object{
        val BASE_IMAGEM = "https://image.tmdb.org/t/p/original/"
    }

        private val BASE_URL = "https://api.themoviedb.org/3/"
        val api_key = "ae69ec81dd60f4108a88423265126573"
        val language = "pt-br"

        private fun retrofitInstance() : Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()


        fun getRetrofitTask() = retrofitInstance().create(MovieApi::class.java)
}