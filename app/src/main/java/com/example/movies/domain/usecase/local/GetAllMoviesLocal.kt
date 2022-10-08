package com.example.movies.domain.usecase.local

import com.example.core.domain.model.Movie
import com.example.movies.framework.db.repository.MovieLocalRepository


interface GetAllMoviesLocalUseCase {
    suspend operator fun invoke(): List<Movie>
}

class GetAllMoviesLocal (
    private val movieLocalRepository: MovieLocalRepository
) : GetAllMoviesLocalUseCase {
    override suspend fun invoke(): List<Movie> = movieLocalRepository.getAllMovie()
}