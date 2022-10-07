package com.example.movies.domain.usecase.local

import com.example.movies.data.database.repository.MovieLocalRepository

interface VerificarMovieUseCase {
    suspend operator fun invoke(id:Int) :Boolean
}
class VerificarMovieImpl(
    private val movieLocalRepository: MovieLocalRepository
) : VerificarMovieUseCase{
    override suspend operator fun invoke(id:Int): Boolean = movieLocalRepository.verificarFilme(id.toLong())
}
