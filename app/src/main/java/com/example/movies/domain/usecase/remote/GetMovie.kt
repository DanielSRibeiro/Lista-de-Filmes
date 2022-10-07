package com.example.movies.domain.usecase.remote

import com.example.movies.data.network.repository.MovieRepository
import com.example.movies.data.network.model.ResultsMoviesResponseDto
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Resource

interface MovieUseCase{
    suspend operator fun invoke(name:String?): Resource<List<Movie>>
}
class GetMovie(
    val repository: MovieRepository
) :MovieUseCase {
    override suspend operator fun invoke(name:String?): Resource<List<Movie>> =
        name?.let {
            repository.getSearchMovies(name.toString())
        } ?: repository.getAllPopulars()
}
