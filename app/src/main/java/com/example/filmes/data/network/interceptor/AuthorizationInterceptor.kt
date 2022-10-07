package com.example.filmes.data.network.interceptor

import com.example.filmes.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestUrl = chain.request().url

        val newUrl = requestUrl.newBuilder()
            .addQueryParameter(API_KEY, BuildConfig.USER_KEY)
            .addQueryParameter(LANGUAGE,"pt-br")
            .build()

        return chain.proceed(
            request.newBuilder()
                .url(newUrl)
                .build()
        )
    }

    companion object {
        const val API_KEY = "api_key"
        const val LANGUAGE = "language"
    }
}