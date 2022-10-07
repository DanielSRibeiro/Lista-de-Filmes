package com.example.movies.domain.usecase.local

import com.example.movies.data.database.repository.MovieLocalRepository

interface DeleteMovieCaseUse {
    suspend operator fun invoke(id:Int)
}
class DeleteMovieImpl(
    private val movieLocalRepository: MovieLocalRepository
) : DeleteMovieCaseUse{
    override suspend operator fun invoke(id:Int) = movieLocalRepository.deleteMovie(id.toLong())
}
