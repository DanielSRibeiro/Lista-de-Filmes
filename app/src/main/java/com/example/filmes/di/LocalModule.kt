package com.example.filmes.di

import com.example.filmes.data.local.AppDatabase
import com.example.filmes.data.local.repository.MovieDataSource
import com.example.filmes.data.local.repository.MovieLocalRepository
import com.example.filmes.domain.usecase.local.*
import com.example.filmes.presentation.view.details.ViewModelLocal
import com.example.filmes.presentation.viewmodel.local.DeleteViewModel
import com.example.filmes.presentation.viewmodel.local.InsertViewModel
import com.example.filmes.presentation.viewmodel.local.SelectViewModel
import com.example.filmes.presentation.viewmodel.local.VerificarViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val daoModule = module {

    //TODO:Remover esses 4 viewModels e os seus testes
    viewModel { InsertViewModel(insertMovieUseCase = get()) }
    viewModel { VerificarViewModel(verificarMovieUseCase = get()) }
    viewModel { DeleteViewModel(deleteMovieUseCase = get()) }
    viewModel { SelectViewModel(selectMovieUseCase = get()) }

    viewModel {
        ViewModelLocal(
            insertMovieUseCase = get(),
            verificarMovieUseCase = get(),
            deleteMovieUseCase = get(),
            categoriesUseCase = get(),
            selectMovieUseCase = get()
        )
    }

    single {
        MovieDataSource(AppDatabase.getInstance(androidContext()).movieDAO
        ) as MovieLocalRepository
    }

    //select and like
    single { VerificarMovieImpl(movieLocalRepository = get()) as VerificarMovieUseCase}
    single { SelectMovieImpl(movieLocalRepository = get()) as SelectMovieUseCase}

    //insert
    single { InsertMovieImpl(movieLocalRepository = get()) as InsertMovieUseCase}

    //delete
    single { DeleteMovieImpl(movieLocalRepository = get()) as DeleteMovieCaseUse}
}