package com.example.filmes.di

import com.example.filmes.data.remote.network.ApiService
import com.example.filmes.data.remote.network.RetrofitTask
import com.example.filmes.data.remote.repository.CategoriesImpl
import com.example.filmes.data.remote.repository.CategoriesRepository
import com.example.filmes.data.remote.repository.MovieImpl
import com.example.filmes.data.remote.repository.MovieRepository
import com.example.filmes.domain.usecase.remote.CategoriesUseCase
import com.example.filmes.domain.usecase.remote.GetCategories
import com.example.filmes.domain.usecase.remote.GetMovie
import com.example.filmes.domain.usecase.remote.MovieUseCase
import com.example.filmes.presentation.viewmodel.remote.CategoriesViewModel
import com.example.filmes.presentation.viewmodel.remote.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val movieModule = module {
    viewModel { MovieViewModel( getMovie = get()) }
    viewModel { CategoriesViewModel(categoriesUseCase = get()) }

    single { RetrofitTask().getRetrofitTask() as ApiService }

    //movie
    single { MovieImpl(apiService = get()) as MovieRepository}
    single { GetMovie(movieRepository = get() as MovieRepository) as MovieUseCase}

    //categorie
    single { CategoriesImpl(apiService = get()) as CategoriesRepository}
    single { GetCategories(categoriesRepository = get()) as CategoriesUseCase}
}