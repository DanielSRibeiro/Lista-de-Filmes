package com.example.filmes.domain.usecase.local

import com.example.filmes.data.local.repository.MovieLocalRepository

class DeleteMovieImpl(
    private val movieLocalRepository: MovieLocalRepository
) : DeleteMovieCaseUse{
    override suspend operator fun invoke(id:Int) = movieLocalRepository.deleteMovie(id.toLong())
}

interface DeleteMovieCaseUse {
    suspend operator fun invoke(id:Int)
}