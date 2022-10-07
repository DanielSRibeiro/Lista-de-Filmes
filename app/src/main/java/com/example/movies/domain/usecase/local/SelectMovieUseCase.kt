package com.example.movies.domain.usecase.local

import com.example.movies.data.database.entity.MovieEntity
import com.example.movies.data.database.repository.MovieLocalRepository

class SelectMovieImpl(
    private val movieLocalRepository: MovieLocalRepository
) : SelectMovieUseCase {
    override operator fun invoke(nome: String?): List<MovieEntity> {
        return if(nome == null) movieLocalRepository.getAllMovie()
        else movieLocalRepository.getSearchName(nome!!)
    }
}

interface SelectMovieUseCase {
    operator fun invoke(nome:String?): List<MovieEntity>
}