package com.example.filmes.di

import com.example.filmes.data.local.AppDatabase
import com.example.filmes.data.local.repository.MovieDataSource
import com.example.filmes.data.local.repository.MovieLocalRepository
import com.example.filmes.domain.usecase.local.*
import com.example.filmes.presentation.fragment.LocalViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val daoModule = module {

    viewModel {
        LocalViewModel(
            insertMovieUseCase = get(),
            verificarMovieUseCase = get(),
            deleteMovieUseCase = get(),
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