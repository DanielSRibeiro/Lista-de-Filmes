package com.example.core.data.repository

import com.example.core.domain.model.Movie

interface MovieLocalRepository {

    suspend fun insertMovie(movie: Movie): Long

    suspend fun deleteMovie(id: Long)

    fun checkMovieState(id: Long): Boolean

    suspend fun getAllMovie(): List<Movie>

    suspend fun getSearchName(title: String): List<Movie>
}