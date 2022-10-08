package com.example.movies.domain.usecase.remote

import com.example.core.data.repository.MovieRepository
import com.example.movies.domain.model.Movie
import com.example.core.domain.model.Resource

interface SearchMoviesUseCase{
    suspend operator fun invoke(name:String): Resource<List<Movie>>
}
class SearchMovies(
    val repository: MovieRepository<Movie>
) :SearchMoviesUseCase {
    override suspend operator fun invoke(name:String): Resource<List<Movie>> = repository.getSearchMovies(name)
}
