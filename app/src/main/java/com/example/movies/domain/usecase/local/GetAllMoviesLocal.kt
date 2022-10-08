package com.example.movies.domain.usecase.local

import com.example.movies.framework.database.repository.MovieLocalRepository
import com.example.movies.domain.model.Movie

interface GetAllMoviesLocalUseCase {
    suspend operator fun invoke(): List<Movie>
}

class GetAllMoviesLocal (
    private val movieLocalRepository: MovieLocalRepository
) : GetAllMoviesLocalUseCase {
    override suspend fun invoke(): List<Movie> = movieLocalRepository.getAllMovie()
}