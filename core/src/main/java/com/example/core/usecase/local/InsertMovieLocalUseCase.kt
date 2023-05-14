package com.example.core.usecase.local

import com.example.core.data.repository.MovieLocalRepository
import com.example.core.domain.model.Movie

interface InsertMovieLocalUseCase{
    suspend operator fun invoke(movie: Movie) : Long
}
class InsertMovieLocalUseCaseImpl(
    private val movieLocalRepository: MovieLocalRepository
) : InsertMovieLocalUseCase {
    override suspend operator fun invoke(movie: Movie) = movieLocalRepository.insertMovie(movie)
}