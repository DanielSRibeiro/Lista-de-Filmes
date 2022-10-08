package com.example.movies.framework.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movies.framework.network.api.MovieApi
import com.example.movies.framework.network.model.MovieResponseDto

class MoviePagingSource(
    val movieMovieApi: MovieApi,
    val name: String
) : PagingSource<Int, MovieResponseDto>() {

    override fun getRefreshKey(state: PagingState<Int, MovieResponseDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponseDto> {
        return try {
            val nextPage = params.key ?: 1

            val response = movieMovieApi.getAllPopularMovies(page =  nextPage)

//            if(response.isSuccessful){
                LoadResult.Page(
                    data = response.body()!!.data,
                    prevKey = null,
                    nextKey = nextPage + 1
                )
//            }

        } catch (exception : Exception){
            LoadResult.Error(exception)
        }
    }

}