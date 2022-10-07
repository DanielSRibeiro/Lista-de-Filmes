package com.example.filmes.domain.usecase.local

import com.example.filmes.data.database.repository.MovieLocalRepository

interface VerificarMovieUseCase {
    suspend operator fun invoke(id:Int) :Boolean
}

class VerificarMovieImpl(
    private val movieLocalRepository: MovieLocalRepository
) : VerificarMovieUseCase{
    override suspend operator fun invoke(id:Int): Boolean = movieLocalRepository.verificarFilme(id.toLong())
}
