package com.example.filmes.domain.usecase.local

import com.example.filmes.data.local.repository.MovieLocalRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class SelectMovieImplTest{
    val repository: MovieLocalRepository = mockk()

    @Test
    fun `Quando o invoke do SelectMovieImple tiver o paramento null deve chamar o getAllMovie`(){
        coEvery { repository.getAllMovie() } returns arrayListOf()

        runBlockingTest { SelectMovieImpl(repository).invoke(null) }

        coVerify { repository.getAllMovie() }
    }

    @Test
    fun `Quando o invoke do SelectMovieImple tiver paramentro diferente de null deve chamar o getSearchName`(){
        val name = "Harry"
        coEvery { repository.getSearchName(name) } returns arrayListOf()

        runBlockingTest { SelectMovieImpl(repository).invoke(name) }

        coVerify { repository.getSearchName(name) }
    }
}