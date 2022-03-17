package com.example.filmes.presentation.fragment.state

import com.example.filmes.domain.model.ResultsMoviesDto

sealed class GetMoviesState {
    class Success(var resultsMovies: ResultsMoviesDto) : GetMoviesState()
    class ErrorNetwork(var message: String) : GetMoviesState()
    class ErrorRequestNotFound(var message: String) : GetMoviesState()
    class ErrorNotSearch(var message: String) : GetMoviesState()
}