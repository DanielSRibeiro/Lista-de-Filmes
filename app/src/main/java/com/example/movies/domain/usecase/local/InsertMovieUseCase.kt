package com.example.movies.domain.usecase.local

import com.example.movies.data.database.repository.MovieLocalRepository
import com.example.movies.data.network.model.MovieDto

class InsertMovieImpl(
    private val movieLocalRepository: MovieLocalRepository
) : InsertMovieUseCase{
    override suspend operator fun invoke(movie: MovieDto, data:String) = movieLocalRepository.insertMovie(movie, data)
}


interface InsertMovieUseCase{
    suspend operator fun invoke(movie: MovieDto, data:String) : Long
}