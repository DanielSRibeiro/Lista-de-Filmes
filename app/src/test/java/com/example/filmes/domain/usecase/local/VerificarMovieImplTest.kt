package com.example.filmes.domain.usecase.local

import com.example.filmes.data.local.repository.MovieLocalRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class VerificarMovieImplTest{

    val dataSource: MovieLocalRepository = mockk()

    @Test
    fun `Quando invoke do VerificarMovieImpl for chamado deve ser chamado o verificarFilme`(){
        val numero = 1
        coEvery { dataSource.verificarFilme(numero.toLong()) } returns true

        runBlockingTest { VerificarMovieImpl(dataSource).invoke(numero) }

        coVerify { dataSource.verificarFilme(numero.toLong()) }
    }
}