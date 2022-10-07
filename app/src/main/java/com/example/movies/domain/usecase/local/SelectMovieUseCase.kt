package com.example.movies.domain.usecase.local

import com.example.movies.data.database.repository.MovieLocalRepository
import com.example.movies.domain.model.Movie

interface SelectMovieUseCase {
    operator fun invoke(nome:String?): List<Movie>
}
class SelectMovieImpl(
    private val movieLocalRepository: MovieLocalRepository
) : SelectMovieUseCase {
    override operator fun invoke(nome: String?): List<Movie> {
        return if(nome == null) movieLocalRepository.getAllMovie()
        else movieLocalRepository.getSearchName(nome)
    }
}
