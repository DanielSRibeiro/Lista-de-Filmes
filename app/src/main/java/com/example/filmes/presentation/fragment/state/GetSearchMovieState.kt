package com.example.filmes.presentation.fragment.state

import com.example.filmes.domain.model.ResultsMoviesDto

sealed class GetSearchMovieState {
    class Success(resultsMovies: ResultsMoviesDto) : GetSearchMovieState()
    class ErrorNetwork(message: String) : GetSearchMovieState()
    class ErrorRequestNotFound(message: String) : GetSearchMovieState()
}