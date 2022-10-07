package com.example.movies.data.database.repository

import com.example.movies.data.database.dao.MovieDao
import com.example.movies.data.database.entity.MovieEntity
import com.example.movies.data.network.model.MovieDto
import com.example.movies.utilis.JsonService

class MovieDataSource(
    private var movieDao: MovieDao
) : MovieLocalRepository {

    override suspend fun insertMovie(movie: MovieDto, data:String): Long {
        val generos = JsonService.fromJson(movie.genreIds)

        val movieEntity = MovieEntity(
            id = movie.id.toLong(),
            posterFilme = movie.posterPath,
            tituloFilme = movie.title,
            sinopse = movie.overview,
            notaMedia = movie.note,
            dataLancamento = data,
            adult = movie.adult,
            backdropPath = movie.backdropPath,
            originalLanguage = movie.originalLanguage,
            originalTitle = movie.originalTitle,
            popularity = movie.popularity,
            video = movie.video,
            voteCount = movie.voteCount,
            generosIds = generos
        )

        return movieDao.insertMovie(movieEntity)
    }

    override suspend fun deleteMovie(id: Long) {
        movieDao.deleteMovie(id)
    }

    override suspend fun verificarFilme(id: Long): Boolean {
        return movieDao.verificarFilme(id)
    }

    override fun getAllMovie(): List<MovieEntity> {
        return movieDao.getAllMovie()
    }

    override fun getSearchName(titulo: String): List<MovieEntity> {
        return movieDao.getSearchName(titulo)
    }
}