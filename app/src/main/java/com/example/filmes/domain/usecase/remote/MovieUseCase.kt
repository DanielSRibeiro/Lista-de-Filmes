package com.example.filmes.domain.usecase.remote

import android.util.Log
import com.example.filmes.utilis.TAG_MOVIE
import com.example.filmes.data.remote.repository.MovieRepository
import com.example.filmes.domain.model.ResultsMoviesDto

class GetMovie(val movieRepository: MovieRepository) :MovieUseCase{

    override suspend operator fun invoke(name:String?): ResultsMoviesDto = try {
        if(name == null) movieRepository.getAllPopulars()
        else movieRepository.getSearchMovies(name)
    }catch (ex:Exception){
        Log.d(TAG_MOVIE, "movieUseCase: $ex")
        ResultsMoviesDto(0, arrayListOf(), 0, 0)
    }
}

interface MovieUseCase{
    suspend operator fun invoke(name:String?): ResultsMoviesDto
}