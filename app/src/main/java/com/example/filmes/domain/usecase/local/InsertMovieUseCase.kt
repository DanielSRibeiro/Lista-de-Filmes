package com.example.filmes.domain.usecase.local

import com.example.filmes.data.local.repository.MovieLocalRepository
import com.example.filmes.domain.model.MovieDto

class InsertMovieImpl(
    private val movieLocalRepository: MovieLocalRepository
) : InsertMovieUseCase{
    override suspend operator fun invoke(movie: MovieDto, data:String) = movieLocalRepository.insertMovie(movie, data)
}


interface InsertMovieUseCase{
    suspend operator fun invoke(movie: MovieDto, data:String) : Long
}