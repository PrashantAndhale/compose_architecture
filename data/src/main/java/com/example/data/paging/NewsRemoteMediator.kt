package com.example.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.map
import com.example.data.room.NewsDAO
import com.example.data.room.NewsKey
import com.example.domain.model.Newspapers
import com.example.domain.repository.PagerMoviesRepository
import jakarta.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator @Inject constructor(
    private val repository: PagerMoviesRepository,
    private val movieDAO: NewsDAO
) : RemoteMediator<Int, Newspapers>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Newspapers>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.APPEND -> {
                    val remoteKeys = getLastKey(state)
                    remoteKeys?.next
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.REFRESH -> {
                    val remoteKey = getClosestKey(state)
                    remoteKey?.next?.minus(1) ?: 1
                }
            }

            val response = repository.getPagerNews()
            var endOfPaginationReached = false
            val news = mutableListOf<Newspapers>()

            response.collect { pagingData ->
                pagingData.map { news.add(it) }
                endOfPaginationReached = news.isEmpty()
            }

            if (loadType == LoadType.REFRESH) {
                movieDAO.deleteAllNews()
                movieDAO.deleteAllNewsKey()
            }

            movieDAO.insertAllNews(news)
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getLastKey(state: PagingState<Int, Newspapers>): NewsKey? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data
            ?.lastOrNull()
            ?.let { it.lccn?.let { it1 -> movieDAO.getAllKeys(it1) } }
    }

    private suspend fun getClosestKey(state: PagingState<Int, Newspapers>): NewsKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let { movieItem ->
                movieItem.lccn?.let { movieDAO.getAllKeys(it) }
            }
        }
    }
}
