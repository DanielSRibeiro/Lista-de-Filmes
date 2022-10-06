package com.example.filmes.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.filmes.data.remote.network.ApiService
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.domain.usecase.remote.MovieUseCase

class MoviePagingSource(
    val movieApiService: ApiService,
    val name: String
) : PagingSource<Int, MovieDto>() {

    override fun getRefreshKey(state: PagingState<Int, MovieDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {
        return try {
            val nextPage = params.key ?: 1

            val response = movieApiService.getAllPopularMovies(page =  nextPage)

//            if(response.isSuccessful){
                LoadResult.Page(
                    data = response.body()!!.movieList,
                    prevKey = null,
                    nextKey = nextPage + 1
                )
//            }

        } catch (exception : Exception){
            LoadResult.Error(exception)
        }
    }

}