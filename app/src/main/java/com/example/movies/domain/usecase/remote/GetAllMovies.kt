package com.example.movies.domain.usecase.remote

import com.example.core.data.repository.MovieRepository
import com.example.core.domain.model.Movie
import com.example.core.domain.model.Resource

interface GetAllMoviesUseCase{
    suspend operator fun invoke(): Resource<List<Movie>>
}
class GetAllMovies(
    val repository: MovieRepository<Movie>
) : GetAllMoviesUseCase {
    override suspend fun invoke(): Resource<List<Movie>> = repository.getAllPopulars()
}