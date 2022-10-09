package com.example.core.usecase.local

import com.example.core.data.datasource.MovieLocalRepository
import com.example.core.domain.model.Movie

interface SearchMovieLocalUseCase {
    suspend operator fun invoke(title:String): List<Movie>
}
class SearchMovieLocalLocalImpl(
    private val movieLocalRepository: MovieLocalRepository
) : SearchMovieLocalUseCase {
    override suspend operator fun invoke(title: String): List<Movie> =  movieLocalRepository.getSearchName(title)
}
