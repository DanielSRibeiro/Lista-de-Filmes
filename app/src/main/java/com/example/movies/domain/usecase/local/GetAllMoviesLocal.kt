package com.example.movies.domain.usecase.local

import com.example.movies.data.database.repository.MovieLocalRepository
import com.example.movies.domain.model.Movie

interface GetAllMoviesLocalUseCase {
    operator fun invoke(): List<Movie>
}

class GetAllMoviesLocal (
    private val movieLocalRepository: MovieLocalRepository
) : GetAllMoviesLocalUseCase {
    override fun invoke(): List<Movie> = movieLocalRepository.getAllMovie()
}