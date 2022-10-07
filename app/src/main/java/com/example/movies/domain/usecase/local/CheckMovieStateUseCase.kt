package com.example.movies.domain.usecase.local

import com.example.movies.data.database.repository.MovieLocalRepository

interface CheckMovieStateUseCase {
    suspend operator fun invoke(id:Int) :Boolean
}
class CheckMovieStateImpl(
    private val repository: MovieLocalRepository
) : CheckMovieStateUseCase{
    override suspend operator fun invoke(id:Int): Boolean = repository.checkMovieState(id.toLong())
}
