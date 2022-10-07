package com.example.movies.di

import android.content.Context
import com.example.movies.BuildConfig
import com.example.movies.data.database.AppDatabase
import com.example.movies.data.database.dao.MovieDao
import com.example.movies.data.database.repository.MovieDataSource
import com.example.movies.data.database.repository.MovieLocalRepository
import com.example.movies.data.network.MovieApi
import com.example.movies.data.network.interceptor.AuthorizationInterceptor
import com.example.movies.data.network.repository.CategoriesImpl
import com.example.movies.data.network.repository.CategoriesRepository
import com.example.movies.data.network.repository.MovieImpl
import com.example.movies.data.network.repository.MovieRepository
import com.example.movies.domain.usecase.local.*
import com.example.movies.domain.usecase.remote.CategoriesUseCase
import com.example.movies.domain.usecase.remote.GetCategories
import com.example.movies.domain.usecase.remote.GetMovie
import com.example.movies.domain.usecase.remote.MovieUseCase
import com.example.movies.presentation.fragment.LocalViewModel
import com.example.movies.presentation.fragment.details.DetailsViewModel
import com.example.movies.presentation.fragment.popular_and_favorite.screens.popular.MovieViewModel
import okhttp3.Interceptor
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
        fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
            setLevel(
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else HttpLoggingInterceptor.Level.NONE
            )
        }
        fun provideInterceptor() = AuthorizationInterceptor()

        fun provideOkHttpClient(
            interceptor: Interceptor,
            loggingInterceptor: HttpLoggingInterceptor
        ) : OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(interceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build()
        }

        fun provideGsonConverter() = GsonConverterFactory.create()

        fun provideRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(provideGsonConverter())
                .client(
                    provideOkHttpClient(provideInterceptor(), provideLoggingInterceptor())
                )
                .build()
        }
        fun provideApiService() = provideRetrofit().create(MovieApi::class.java)

        single<MovieApi> { provideApiService() }
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