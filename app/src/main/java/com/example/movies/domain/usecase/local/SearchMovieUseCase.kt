package com.example.movies.domain.usecase.local

import com.example.core.domain.model.Movie
import com.example.movies.framework.db.repository.MovieLocalRepository

interface SelectMovieUseCase {
    suspend operator fun invoke(title:String): List<Movie>
}
class SearchMovieLocal(
    private val movieLocalRepository: MovieLocalRepository
) : SelectMovieUseCase {
    override suspend operator fun invoke(title: String): List<Movie> =  movieLocalRepository.getSearchName(title)
}
