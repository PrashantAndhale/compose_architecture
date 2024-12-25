package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.mappers.toDomain
import com.example.data.network.ApiService
import com.example.data.network.NetworkConnectionChecker
import com.example.data.network.NoInternetConnectionException
import com.example.data.room.MovieDAO
import com.example.domain.model.MoviesItem

class MoviesPagingSource(
    private val apiService: ApiService,
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val dao: MovieDAO
) : PagingSource<Int, MoviesItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesItem> {
        val page = params.key ?: 1
        return try {
            if (networkConnectionChecker.isConnected()) {
                val response = apiService.getMovies()
                val movies = response.body()?.toDomain() ?: emptyList()
                 LoadResult.Page(
                    data =movies,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (movies.isEmpty()) null else page + 1
                )
            } else {
                throw NoInternetConnectionException("No internet connection")
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MoviesItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
