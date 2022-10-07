package com.example.movies.domain.usecase.local

import com.example.movies.data.database.repository.MovieLocalRepository

interface CheckMovieStateUseCase {
    suspend operator fun invoke(id:Int) :Boolean
}
class CheckMovieStateImpl(
    private val movieLocalRepository: MovieLocalRepository
) : CheckMovieStateUseCase{
    override suspend operator fun invoke(id:Int): Boolean = movieLocalRepository.verificarFilme(id.toLong())
}
