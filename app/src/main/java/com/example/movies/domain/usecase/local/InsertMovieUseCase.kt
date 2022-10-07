package com.example.movies.domain.usecase.local

import com.example.movies.data.database.repository.MovieLocalRepository
import com.example.movies.domain.model.Movie

interface InsertMovieUseCase{
    suspend operator fun invoke(movie: Movie) : Long
}
class InsertMovieImpl(
    private val movieLocalRepository: MovieLocalRepository
) : InsertMovieUseCase{
    override suspend operator fun invoke(movie: Movie) = movieLocalRepository.insertMovie(movie)
}