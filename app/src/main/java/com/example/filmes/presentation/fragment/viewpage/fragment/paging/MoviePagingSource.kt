package com.example.filmes.presentation.fragment.viewpage.fragment.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.domain.usecase.remote.MovieUseCase

class MoviePagingSource(
    val useCase: MovieUseCase,
    val query: MutableLiveData<String>
) : PagingSource<Int, MovieDto>() {

    override fun getRefreshKey(state: PagingState<Int, MovieDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {
        return try {
            val page = params.key ?: 1
            val response = useCase.invoke(query.value, page)

            LoadResult.Page(
                data = response.movieList,
                prevKey = null,
                nextKey = if(response.movieList.isNullOrEmpty())null else page+1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}