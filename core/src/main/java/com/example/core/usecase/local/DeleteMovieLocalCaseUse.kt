package com.example.core.usecase.local

import com.example.core.data.datasource.MovieLocalRepository

interface DeleteMovieLocalCaseUse {
    suspend operator fun invoke(id:Int)
}
class DeleteMovieLocalCaseUseImpl(
    private val movieLocalRepository: MovieLocalRepository
) : DeleteMovieLocalCaseUse {
    override suspend operator fun invoke(id:Int) = movieLocalRepository.deleteMovie(id.toLong())
}
