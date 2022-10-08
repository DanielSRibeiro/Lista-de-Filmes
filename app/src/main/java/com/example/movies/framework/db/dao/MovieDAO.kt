package com.example.movies.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movies.framework.db.entity.MovieEntity

@Dao
interface MovieDAO {

    @Insert
    suspend fun insertMovie(movieEntity: MovieEntity):Long

    @Query("DELETE FROM movie WHERE id=:id")
    suspend fun deleteMovie(id: Long)

    @Query("SELECT * FROM movie WHERE id=:id")
    fun checkMovieState(id: Long):Boolean

    @Query("SELECT * FROM movie")
    suspend fun getAllMovie(): List<MovieEntity>

    @Query("SELECT * FROM movie WHERE title LIKE '%' || :title || '%'")
    suspend fun getSearchName(title: String): List<MovieEntity>?
}