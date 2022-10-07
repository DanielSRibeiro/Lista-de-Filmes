package com.example.movies.domain.model

sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val data : T) : Resource<T>()
    object Fail : Resource<Nothing>()
}
