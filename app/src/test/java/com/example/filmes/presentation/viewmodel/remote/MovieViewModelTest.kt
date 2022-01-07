package com.example.filmes.presentation.viewmodel.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.domain.model.ResultsMoviesDto
import com.example.filmes.domain.usecase.remote.GetMovie
import com.example.filmes.presentation.fragment.viewpage.fragment.popular.MovieViewModel
import io.mockk.coEvery
import io.mockk.coVerifyOrder
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
import java.util.*
import kotlin.collections.ArrayList

@ExperimentalCoroutinesApi
class MovieViewModelTest{

    @get:Rule
    val rule = InstantTaskExecutorRule()
    val movieUse = mockk<GetMovie>()
    val movieLiveData = mockk<Observer<ArrayList<MovieDto>>>()
    val erroLiveData = mockk<Observer<Boolean>>()
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
    fun `Quando o getAllMovie for chamado com sucesso deve dá o valor para o movieLiveData`(){
        val viewModel = viewModelInstance()
        val listaFilmes = mockListaFilmes()
        val resultsMovie = ResultsMoviesDto(listaFilmes, 1,1)
        coEvery { movieUse.invoke(null) } returns resultsMovie

        viewModel.getAllMovies(null)

        coVerifyOrder {
            movieUse.invoke(null)
            movieLiveData.onChanged(resultsMovie.movieList)
        }
    }

    @Test
    fun `Quando o getAllMovie trazer uma resultsMovie com lista vazia de filmes eo parametro dela nao for null o valor de  erroLiveData sera false`(){
        val viewModel = viewModelInstance()
        val resultsMovie = ResultsMoviesDto(arrayListOf(), 0,0)
        coEvery { movieUse.invoke("Harry Portter") } returns resultsMovie

        viewModel.getAllMovies("Harry Portter")

        coVerifyOrder {
            erroLiveData.onChanged(false)
        }
    }

    @Test
    fun `Quando o getAllMovie trazer um resultsMovie com lista vazia de filmes e parametro deve se null valor de erroLiveData sera true`(){
        val viewModel = viewModelInstance()
        val resultsMovie = ResultsMoviesDto(arrayListOf(), 0,0)
        coEvery { movieUse.invoke(null) } returns resultsMovie

        viewModel.getAllMovies(null)

        coVerifyOrder {
            erroLiveData.onChanged(true)
        }
    }

    private fun viewModelInstance(): MovieViewModel {
        val viewModel = MovieViewModel(movieUse)
        viewModel.movieList.observeForever(movieLiveData)
        viewModel.error.observeForever(erroLiveData)
        return viewModel
    }


    private fun mockListaFilmes():ArrayList<MovieDto>{
        return arrayListOf(
            MovieDto("Filme1","","", 1.0, Date(),intArrayOf(1,3),
            false,"",0,"","",0.0,false,0),
            MovieDto("Filme2","","", 1.0, Date(), intArrayOf(1,3),
                false,"",0,"","",0.0,false,0),
            MovieDto("Filme3","","", 1.0, Date(), intArrayOf(1,3),
                false,"",0,"","",0.0,false,0)
        )
    }
}