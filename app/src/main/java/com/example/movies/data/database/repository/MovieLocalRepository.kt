package com.example.movies.data.database.repository

import com.example.movies.domain.model.Movie

interface MovieLocalRepository {

    suspend fun insertMovie(Movie: Movie, data:String): Long

    suspend fun deleteMovie(id: Long)

    suspend fun verificarFilme(id: Long): Boolean

    fun getAllMovie(): List<Movie>

    fun getSearchName(titulo: String): List<Movie>
}