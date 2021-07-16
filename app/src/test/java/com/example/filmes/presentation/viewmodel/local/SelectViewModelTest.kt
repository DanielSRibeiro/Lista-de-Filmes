package com.example.filmes.presentation.viewmodel.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.filmes.data.local.entity.MovieEntity
import com.example.filmes.domain.usecase.local.SelectMovieImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SelectViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    val selectUse = mockk<SelectMovieImpl>()
    val listaSalvaLiveData = mockk<Observer<List<MovieEntity>>>()
    private val mainThreadSurrogate = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.cleanupTestCoroutines()
    }

    @Test
    fun `Quando o metodo getSeachMovie for chamado com sucesso deve retornar uma Lista de MovieEntity`() {
        val viewMovie = viewModelInstance()
        val mockMovie = listMovie()
        coEvery { selectUse.invoke("Harry Potter") } returns mockMovie

        viewMovie.getSeachMovie("Harry Potter")

        coVerify { listaSalvaLiveData.onChanged(mockMovie) }
    }

    private fun listMovie(): List<MovieEntity> {
        return listOf(MovieEntity(1,"1,23,4","","Harry Potter 1"," 1.0, ", 1.1,
            "data",false,"","","",0.0,false,0),
                MovieEntity(1,"1,23,4","","Harry Potter 2"," 1.0, ", 1.1,
            "data",false,"","","",0.0,false,0)
        )
    }

    private fun viewModelInstance(): SelectViewModel {
        val viewModel = SelectViewModel(selectUse)
        viewModel.listaSalva.observeForever(listaSalvaLiveData)
        return viewModel
    }
}