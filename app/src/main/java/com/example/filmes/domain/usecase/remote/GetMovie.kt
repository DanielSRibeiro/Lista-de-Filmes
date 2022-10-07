package com.example.filmes.domain.usecase.remote

import com.example.filmes.data.network.repository.MovieRepository
import com.example.filmes.data.network.model.ResultsMoviesDto

class GetMovie(val movieRepository: MovieRepository) :MovieUseCase {
    override suspend operator fun invoke(name:String?): ResultsMoviesDto? =
        name?.let {
            movieRepository.getSearchMovies(name.toString())
        } ?: movieRepository.getAllPopulars()
}

interface MovieUseCase{
    suspend operator fun invoke(name:String?): ResultsMoviesDto?
}