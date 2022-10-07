package com.example.movies.domain.usecase.local

import com.example.movies.data.database.repository.MovieLocalRepository
import com.example.movies.data.network.model.MovieResponseDto
import com.example.movies.domain.model.Movie
import java.util.*

interface InsertMovieUseCase{
    suspend operator fun invoke(movie: Movie) : Long
}
class InsertMovieImpl(
    private val movieLocalRepository: MovieLocalRepository
) : InsertMovieUseCase{
    override suspend operator fun invoke(movie: Movie) = movieLocalRepository.insertMovie(movie)
}
