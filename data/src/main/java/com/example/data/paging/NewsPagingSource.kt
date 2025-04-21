package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.mappers.toDomain
import com.example.data.network.ApiService
import com.example.data.network.NetworkConnectionChecker
import com.example.data.network.NoInternetConnectionException
import com.example.data.room.NewsDAO
import com.example.domain.model.NewsDetails
import com.example.domain.model.Newspapers

class NewsPagingSource(
    private val apiService: ApiService,
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val dao: NewsDAO
) : PagingSource<Int, Newspapers>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Newspapers> {
        val page = params.key ?: 1
        return try {
            if (networkConnectionChecker.isConnected()) {
                val response = apiService.getNews()
                val news = response.body()?.toDomain() ?: emptyList()
                LoadResult.Page(
                    data = news,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (news.isEmpty()) null else page + 1
                )
            } else {
                throw NoInternetConnectionException("No internet connection")
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Newspapers>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}



