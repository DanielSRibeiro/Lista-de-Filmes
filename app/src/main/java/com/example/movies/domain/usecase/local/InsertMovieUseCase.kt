package com.example.movies.domain.usecase.local

import com.example.core.domain.model.Movie
import com.example.movies.framework.db.repository.MovieLocalRepository

interface InsertMovieUseCase{
    suspend operator fun invoke(movie: Movie) : Long
}
class InsertMovieImpl(
    private val movieLocalRepository: MovieLocalRepository
) : InsertMovieUseCase{
    override suspend operator fun invoke(movie: Movie) = movieLocalRepository.insertMovie(movie)
}