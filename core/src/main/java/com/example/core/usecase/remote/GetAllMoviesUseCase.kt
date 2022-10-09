package com.example.core.usecase.remote

import com.example.core.data.repository.MovieRepository
import com.example.core.domain.model.Movie
import com.example.core.domain.model.Resource

interface GetAllMoviesUseCase{
    suspend operator fun invoke(): Resource<List<Movie>>
}
class GetAllMoviesUseCaseImpl(
    val repository: MovieRepository<Movie>
) : GetAllMoviesUseCase {
    override suspend fun invoke(): Resource<List<Movie>> = repository.getAllPopulars()
}