package com.example.filmes.presentation.viewmodel.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.filmes.domain.usecase.local.VerificarMovieImpl
import io.mockk.*
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
class VerificarViewModelTest{

    @get:Rule
    val rule = InstantTaskExecutorRule()
    val verificarUse = mockk<VerificarMovieImpl>()
    val verificarLiveData = mockk<Observer<Boolean>>()
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
    fun `Quando o metodo verificar for chamado deve voltar true no verificarLiveData`(){
        val verificarViewModel = viewModelInstance()
        val jaEstaSalvo = true
        coEvery { verificarUse.invoke(1) } returns jaEstaSalvo

        verificarViewModel.verificar(1)

        coVerify { verificarLiveData.onChanged(true) }
    }

    @Test
    fun `Quando o metodo verificar for chamado deve voltar false no verificarLiveData`(){
        val verificarViewModel = viewModelInstance()
        val jaEstaSalvo = false
        coEvery { verificarUse.invoke(1) } returns jaEstaSalvo

        verificarViewModel.verificar(1)

        coVerify { verificarLiveData.onChanged(false) }
    }

    private fun viewModelInstance(): VerificarViewModel {
        val viewModel = VerificarViewModel(verificarUse)
        viewModel.verificado.observeForever(verificarLiveData)
        return viewModel
    }
}