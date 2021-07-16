package com.example.filmes.domain.usecase.remote

import com.example.filmes.data.remote.repository.MovieImpl
import com.example.filmes.domain.model.ResultsMoviesDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class GetMovieTest{

    val repository: MovieImpl = mockk()

    @Test
    fun `Quando o invoke do GetMovie tiver o paramentro diferente de null deve chamar o getAllPopulars`(){
        coEvery { repository.getAllPopulars() } returns ResultsMoviesDto(arrayListOf(), 0, 0)

        runBlockingTest { GetMovie(repository).invoke(null) }

        coVerify { repository.getAllPopulars() }
    }

    @Test
    fun `Quando o invoke do GetMovie tiver o paramentro diferente de null deve chamar o getSearchMovies`(){
        val name = "Harry"
        coEvery { repository.getSearchMovies(name) } returns ResultsMoviesDto(arrayListOf(), 0, 0)

        runBlockingTest { GetMovie(repository).invoke(name) }

        coVerify { repository.getSearchMovies(name) }
    }
}