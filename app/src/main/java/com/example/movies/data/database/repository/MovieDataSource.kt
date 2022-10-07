package com.example.movies.data.database.repository

import com.example.movies.data.database.dao.MovieDAO
import com.example.movies.data.database.entity.MovieEntity
import com.example.movies.data.database.entity.movieEntityToMovie
import com.example.movies.domain.model.Movie
import com.example.movies.util.Utils

interface MovieLocalRepository {

    suspend fun insertMovie(Movie: Movie): Long

    suspend fun deleteMovie(id: Long)

    fun checkMovieState(id: Long): Boolean

    suspend fun getAllMovie(): List<Movie>

    suspend fun getSearchName(titulo: String): List<Movie>
}

class MovieDataSource(
    private var movieDao: MovieDAO
) : MovieLocalRepository {

    override suspend fun insertMovie(movie: Movie): Long {
        val genreId = Utils.fromJson(movie.genreIds)

        val movieEntity = MovieEntity(
            id = movie.id.toLong(),
            posterPath = movie.posterPath,
            posterUrl = movie.backdropUrl,
            title = movie.title,
            overview = movie.overview,
            notaMedia = movie.note,
            releaseData = movie.releaseData,
            backdropPath = movie.backdropPath,
            genreIds = genreId,
            backdropUrl = movie.backdropUrl
        )

        return movieDao.insertMovie(movieEntity)
    }

    override suspend fun deleteMovie(id: Long) {
        movieDao.deleteMovie(id)
    }

    override fun checkMovieState(id: Long): Boolean {
        return movieDao.checkMovieState(id)
    }

    override suspend fun getAllMovie(): List<Movie> {
        val entity = movieDao.getAllMovie()
        return entity.map { it.movieEntityToMovie() }
    }

    override suspend fun getSearchName(title: String): List<Movie> {
        val entity = movieDao.getSearchName(title)
        return entity?.let {
            it.map { it.movieEntityToMovie() }
        } ?: listOf()
    }
}