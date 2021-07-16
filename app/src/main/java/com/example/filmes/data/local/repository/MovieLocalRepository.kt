package com.example.filmes.data.local.repository

import com.example.filmes.data.local.entity.MovieEntity
import com.example.filmes.domain.model.MovieDto

interface MovieLocalRepository {

    suspend fun insertMovie(movieDto: MovieDto, data:String): Long

    suspend fun deleteMovie(id: Long)

    suspend fun verificarFilme(id: Long): Boolean

    fun getAllMovie(): List<MovieEntity>

    fun getSearchName(titulo: String): List<MovieEntity>
}