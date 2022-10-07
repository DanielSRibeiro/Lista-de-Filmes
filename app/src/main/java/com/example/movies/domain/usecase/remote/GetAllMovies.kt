package com.example.movies.domain.usecase.remote

import com.example.movies.data.network.repository.MovieRepository
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Resource

interface GetAllMoviesUseCase{
    suspend operator fun invoke(): Resource<List<Movie>>
}
class GetAllMovies(
    val repository: MovieRepository
) : GetAllMoviesUseCase {
    override suspend fun invoke(): Resource<List<Movie>> = repository.getAllPopulars()
}