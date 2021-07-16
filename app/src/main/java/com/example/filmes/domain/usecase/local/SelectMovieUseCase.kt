package com.example.filmes.domain.usecase.local

import com.example.filmes.data.local.entity.MovieEntity
import com.example.filmes.data.local.repository.MovieLocalRepository

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