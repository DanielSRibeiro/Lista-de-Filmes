package com.example.movies.domain.usecase.local

import com.example.movies.framework.database.repository.MovieLocalRepository
import com.example.movies.domain.model.Movie

interface SelectMovieUseCase {
    suspend operator fun invoke(title:String): List<Movie>
}
class SearchMovieLocal(
    private val movieLocalRepository: MovieLocalRepository
) : SelectMovieUseCase {
    override suspend operator fun invoke(title: String): List<Movie> =  movieLocalRepository.getSearchName(title)
}
