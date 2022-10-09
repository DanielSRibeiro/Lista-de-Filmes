package com.example.movies.framework.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.domain.model.Movie
import com.example.movies.framework.network.MovieApi
import com.example.movies.framework.network.response.MovieResponseDto
import com.example.movies.framework.network.response.movieResponseDtoToMovie

class MoviePagingSource(
    private val movieApi: MovieApi,
    val name: String?
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val response =
                if(name.isNullOrBlank())
                    movieApi.getAllPopularMovies(page = nextPage)
                else
                    movieApi.getSearchName(name = name)

            LoadResult.Page(
                data = response.body()!!.data.map { it.movieResponseDtoToMovie() },
                prevKey = null,
                nextKey = nextPage + 1
            )

        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}