package com.example.core.usecase.local

import com.example.core.data.repository.MovieLocalRepository

interface CheckStateLocalUseCase {
    suspend operator fun invoke(id:Int) :Boolean
}
class CheckStateLocalUseCaseImpl(
    private val repository: MovieLocalRepository
) : CheckStateLocalUseCase {
    override suspend operator fun invoke(id:Int): Boolean = repository.checkMovieState(id.toLong())
}
