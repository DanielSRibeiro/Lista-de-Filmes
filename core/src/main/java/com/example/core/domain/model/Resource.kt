package com.example.core.domain.model

import com.example.core.ErrorStates

sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val data : T) : Resource<T>()
    data class Fail(val status: ErrorStates, val message: String) : Resource<Nothing>()
}
