package com.example.core.data.datasource

import com.example.core.domain.model.Movie

interface MovieLocalRepository {

    suspend fun insertMovie(Movie: Movie): Long

    suspend fun deleteMovie(id: Long)

    fun checkMovieState(id: Long): Boolean

    suspend fun getAllMovie(): List<Movie>

    suspend fun getSearchName(titulo: String): List<Movie>
}