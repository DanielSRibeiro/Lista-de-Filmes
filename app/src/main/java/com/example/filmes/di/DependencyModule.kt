package com.example.filmes.di

import android.content.Context
import com.example.filmes.BuildConfig
import com.example.filmes.data.local.AppDatabase
import com.example.filmes.data.local.dao.MovieDao
import com.example.filmes.data.local.repository.MovieDataSource
import com.example.filmes.data.local.repository.MovieLocalRepository
import com.example.filmes.data.remote.ApiService
import com.example.filmes.data.remote.repository.CategoriesImpl
import com.example.filmes.data.remote.repository.CategoriesRepository
import com.example.filmes.data.remote.repository.MovieImpl
import com.example.filmes.data.remote.repository.MovieRepository
import com.example.filmes.domain.usecase.local.*
import com.example.filmes.domain.usecase.remote.CategoriesUseCase
import com.example.filmes.domain.usecase.remote.GetCategories
import com.example.filmes.domain.usecase.remote.GetMovie
import com.example.filmes.domain.usecase.remote.MovieUseCase
import com.example.filmes.presentation.fragment.LocalViewModel
import com.example.filmes.presentation.fragment.details.DetailsViewModel
import com.example.filmes.presentation.fragment.popular_and_favorite.screens.popular.MovieViewModel
import com.example.filmes.utilis.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DependencyModule {
    val moduleApp = module {
        fun dataBase(context: Context): AppDatabase {
            return AppDatabase.getInstance(context)
        }
        fun provideMovieDAO(dataBase: AppDatabase): MovieDao {
            return dataBase.movieDAO
        }
        fun provideRetrofit(): Retrofit {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else HttpLoggingInterceptor.Level.NONE

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)


            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
        }
        fun provideApiService() = provideRetrofit().create(ApiService::class.java)

        single<ApiService> { provideApiService() }
        single<MovieRepository> { MovieImpl(get()) }
        single<MovieUseCase> { GetMovie(get() as MovieRepository) }
        single<CategoriesRepository> { CategoriesImpl(get()) }
        single<CategoriesUseCase> { GetCategories(get()) }
        single<VerificarMovieUseCase> { VerificarMovieImpl(get()) }
        single<SelectMovieUseCase> { SelectMovieImpl(get()) }
        single<InsertMovieUseCase> { InsertMovieImpl(get()) }
        single<DeleteMovieCaseUse> { DeleteMovieImpl(get()) }
        single<AppDatabase> { dataBase(androidContext()) }
        single<MovieDao> { provideMovieDAO(get()) }
        single<MovieLocalRepository> { MovieDataSource(get()) }

        viewModel { MovieViewModel(get()) }
        viewModel { DetailsViewModel(get()) }
        viewModel { LocalViewModel(get(), get(), get(), get()) }
    }
}