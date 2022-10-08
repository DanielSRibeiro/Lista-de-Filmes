package com.example.movies.di

import android.content.Context
import com.example.movies.BuildConfig
import com.example.movies.data.database.AppDatabase
import com.example.movies.data.database.dao.CategoryDAO
import com.example.movies.data.database.dao.MovieDAO
import com.example.movies.data.database.repository.CategoryDataSource
import com.example.movies.data.database.repository.CategoryLocalRepository
import com.example.movies.data.database.repository.MovieDataSource
import com.example.movies.data.database.repository.MovieLocalRepository
import com.example.movies.data.network.api.MovieApi
import com.example.movies.data.network.interceptor.AuthorizationInterceptor
import com.example.movies.data.network.repository.CategoriesImpl
import com.example.movies.data.network.repository.CategoriesRepository
import com.example.movies.data.network.repository.MovieRepositoryImpl
import com.example.movies.data.network.repository.MovieRepository
import com.example.movies.data.network.datasource.CategoryRemoteDataSource
import com.example.movies.data.network.datasource.CategoryRemoteDataSourceImpl
import com.example.movies.data.network.datasource.MovieRemoteDataSource
import com.example.movies.data.network.datasource.MovieRemoteDataSourceImpl
import com.example.movies.domain.usecase.local.*
import com.example.movies.domain.usecase.remote.*
import com.example.movies.presentation.popular_and_favorite.screens.favorite.FavoriteViewModel
import com.example.movies.presentation.details.DetailsViewModel
import com.example.movies.presentation.popular_and_favorite.PopularAndFavoriteViewModel
import com.example.movies.presentation.popular_and_favorite.screens.popular.PopularViewModel
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
        fun provideMovieDAO(dataBase: AppDatabase): MovieDAO {
            return dataBase.movieDAO
        }
        fun provideCategoryDAO(dataBase: AppDatabase): CategoryDAO {
            return dataBase.categoryDAO
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

        fun provideRetrofit(
            client: OkHttpClient
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(provideGsonConverter())
                .client(client)
                .build()
        }
        fun provideApiService(client: OkHttpClient) =
            provideRetrofit(client).create(MovieApi::class.java)

        single<MovieApi> {
            provideApiService(
                provideOkHttpClient(
                    provideInterceptor(),
                    provideLoggingInterceptor()
                )
            )
        }

        single<AppDatabase> { dataBase(androidContext()) }
        single<MovieDAO> { provideMovieDAO(get()) }
        single<CategoryDAO> { provideCategoryDAO(get()) }

        single<MovieRemoteDataSource> { MovieRemoteDataSourceImpl(get()) }
        single<CategoryRemoteDataSource> { CategoryRemoteDataSourceImpl(get()) }

        single<MovieRepository> { MovieRepositoryImpl(get()) }
        single<CategoriesRepository> { CategoriesImpl(get()) }
        single<MovieLocalRepository> { MovieDataSource(get()) }
        single<CategoryLocalRepository> { CategoryDataSource(get()) }

        single<SearchMoviesUseCase> { SearchMovies(get()) }
        single<GetAllCategoriesUseCase> { GetAllCategories(get()) }
        single<CheckMovieStateUseCase> { CheckMovieStateImpl(get()) }
        single<SelectMovieUseCase> { SearchMovieLocal(get()) }
        single<InsertMovieUseCase> { InsertMovieImpl(get()) }
        single<DeleteMovieCaseUse> { DeleteMovieImpl(get()) }
        single<SaveCategoryUseCase> { SaveCategory(get()) }
        single<GetAllMoviesUseCase> { GetAllMovies(get()) }
        single<GetCategoryUseCase> { GetCategory(get()) }
        single<GetAllMoviesLocalUseCase> { GetAllMoviesLocal(get()) }

        viewModel { PopularViewModel(get(), get()) }
        viewModel { DetailsViewModel(get(), get(), get(), get()) }
        viewModel { FavoriteViewModel(get(), get(), get()) }
        viewModel { PopularAndFavoriteViewModel(get(), get()) }
    }
}