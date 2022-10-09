package com.example.core.usecase.remote

import com.example.core.data.repository.MovieRepository
import com.example.core.domain.model.Movie
import com.example.core.domain.model.Resource

interface SearchMoviesUseCase{
    suspend operator fun invoke(name:String): Resource<List<Movie>>
}
class SearchMoviesUseCaseImp(
    val repository: MovieRepository<Movie>
) : SearchMoviesUseCase {
    override suspend operator fun invoke(name:String): Resource<List<Movie>> = repository.getSearchMovies(name)
}
