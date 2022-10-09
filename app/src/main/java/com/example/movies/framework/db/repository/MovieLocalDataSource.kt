package com.example.movies.framework.db.repository

import com.example.core.data.datasource.MovieLocalRepository
import com.example.movies.framework.db.dao.MovieDAO
import com.example.movies.framework.db.entity.MovieEntity
import com.example.movies.framework.db.entity.movieEntityToMovie
import com.example.core.domain.model.Movie
import com.example.movies.util.Utils

class MovieLocalDataSource(
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