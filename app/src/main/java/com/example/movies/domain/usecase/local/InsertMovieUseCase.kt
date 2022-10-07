package com.example.movies.domain.usecase.local

import com.example.movies.data.database.repository.MovieLocalRepository
import com.example.movies.data.network.model.MovieResponseDto
import com.example.movies.domain.model.Movie

class InsertMovieImpl(
    private val movieLocalRepository: MovieLocalRepository
) : InsertMovieUseCase{
    override suspend operator fun invoke(movie: Movie, data:String) = movieLocalRepository.insertMovie(movie, data)
}


interface InsertMovieUseCase{
    suspend operator fun invoke(movie: Movie, data:String) : Long
}