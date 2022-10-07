package com.example.filmes.domain.usecase.local

import com.example.filmes.data.database.repository.MovieLocalRepository

interface DeleteMovieCaseUse {
    suspend operator fun invoke(id:Int)
}

class DeleteMovieImpl(
    private val movieLocalRepository: MovieLocalRepository
) : DeleteMovieCaseUse{
    override suspend operator fun invoke(id:Int) = movieLocalRepository.deleteMovie(id.toLong())
}
