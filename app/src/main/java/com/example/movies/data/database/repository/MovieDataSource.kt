package com.example.movies.data.database.repository

import com.example.movies.data.database.dao.MovieDao
import com.example.movies.data.database.entity.MovieEntity
import com.example.movies.data.database.entity.movieEntityToMovie
import com.example.movies.domain.model.Movie
import com.example.movies.utilis.JsonService

class MovieDataSource(
    private var movieDao: MovieDao
) : MovieLocalRepository {

    override suspend fun insertMovie(movie: Movie, data:String): Long {
        val genreId = JsonService.fromJson(movie.genreIds)

        val movieEntity = MovieEntity(
            id = movie.id.toLong(),
            posterPath = movie.posterPath,
            title = movie.title,
            overview = movie.overview,
            notaMedia = movie.note,
            releaseData = data,
            backdropPath = movie.backdropPath,
            genreIds = genreId
        )

        return movieDao.insertMovie(movieEntity)
    }

    override suspend fun deleteMovie(id: Long) {
        movieDao.deleteMovie(id)
    }

    override suspend fun verificarFilme(id: Long): Boolean {
        return movieDao.verificarFilme(id)
    }

    override fun getAllMovie(): List<Movie> {
        val entity = movieDao.getAllMovie()
        return entity.map { it.movieEntityToMovie() }
    }

    override fun getSearchName(titulo: String): List<Movie> {
        val entity =  movieDao.getSearchName(titulo)
        return entity.map { it.movieEntityToMovie() }
    }
}