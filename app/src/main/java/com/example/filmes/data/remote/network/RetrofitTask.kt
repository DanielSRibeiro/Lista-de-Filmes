package com.example.filmes.data.remote.network

import com.example.filmes.utilis.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitTask {

    private fun retrofitInstance() : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
        .build()


    fun getRetrofitTask() = retrofitInstance().create(ApiService::class.java)
}