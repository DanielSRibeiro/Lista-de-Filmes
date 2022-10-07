package com.example.movies.domain.usecase.remote

import com.example.movies.data.network.repository.MovieRepository
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Resource

interface SearchMoviesUseCase{
    suspend operator fun invoke(name:String): Resource<List<Movie>>
}
class SearchMovies(
    val repository: MovieRepository
) :SearchMoviesUseCase {
    override suspend operator fun invoke(name:String): Resource<List<Movie>> = repository.getSearchMovies(name)
}
