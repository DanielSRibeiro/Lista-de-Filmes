package com.example.core.usecase.local

import com.example.core.data.datasource.MovieLocalRepository
import com.example.core.domain.model.Movie

interface GetAllMoviesLocalUseCase {
    suspend operator fun invoke(): List<Movie>
}

class GetAllMoviesLocalCaseUseImpl (
    private val movieLocalRepository: MovieLocalRepository
) : GetAllMoviesLocalUseCase {
    override suspend fun invoke(): List<Movie> = movieLocalRepository.getAllMovie()
}