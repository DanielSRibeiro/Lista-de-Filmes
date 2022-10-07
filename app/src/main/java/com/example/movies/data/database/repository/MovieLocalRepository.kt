package com.example.movies.data.database.repository

import com.example.movies.data.database.entity.MovieEntity
import com.example.movies.data.network.model.MovieDto

interface MovieLocalRepository {

    suspend fun insertMovie(movieDto: MovieDto, data:String): Long

    suspend fun deleteMovie(id: Long)

    suspend fun verificarFilme(id: Long): Boolean

    fun getAllMovie(): List<MovieEntity>

    fun getSearchName(titulo: String): List<MovieEntity>
}