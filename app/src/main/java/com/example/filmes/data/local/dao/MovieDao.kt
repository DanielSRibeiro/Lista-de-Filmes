package com.example.filmes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.filmes.data.local.entity.MovieEntity

@Dao
interface MovieDao {

    @Insert
    suspend fun insertMovie(movieEntity: MovieEntity):Long

    @Query("DELETE FROM movie WHERE id=:id")
    suspend fun deleteMovie(id: Long)

    @Query("SELECT * FROM movie WHERE id=:id")
    fun verificarFilme(id: Long):Boolean

    @Query("SELECT * FROM movie")
    fun getAllMovie(): List<MovieEntity>

    @Query("SELECT * FROM movie WHERE tituloFilme LIKE '%' || :titulo || '%'")
    fun getSearchName(titulo: String): List<MovieEntity>
}