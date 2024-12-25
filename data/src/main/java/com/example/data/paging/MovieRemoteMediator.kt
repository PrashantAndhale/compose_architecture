package com.example.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.map
import com.example.data.room.MovieDAO
import com.example.data.room.MovieKey
import com.example.domain.model.MoviesItem
import com.example.domain.repository.PagerMoviesRepository
import jakarta.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator @Inject constructor(
    private val repository: PagerMoviesRepository,
    private val movieDAO: MovieDAO
) : RemoteMediator<Int, MoviesItem>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MoviesItem>
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

            val response = repository.getPagerMovies()
            var endOfPaginationReached = false
            val movies = mutableListOf<MoviesItem>()

            response.collect { pagingData ->
                pagingData.map { movies.add(it) }
                endOfPaginationReached = movies.isEmpty()
            }

            if (loadType == LoadType.REFRESH) {
                movieDAO.deleteAllMovies()
                movieDAO.deleteAllMoviesKey()
            }

            movieDAO.insertAllMovies(movies)
            val keys = movies.map {
                MovieKey(
                    id = it.id,
                    prev = if (page == 1) null else page - 1,
                    next = if (endOfPaginationReached) null else page + 1
                )
            }
            movieDAO.insertAllMoviesKey(keys)

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getLastKey(state: PagingState<Int, MoviesItem>): MovieKey? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data
            ?.lastOrNull()
            ?.let { movieDAO.getMovieKey(it.id) }
    }

    private suspend fun getClosestKey(state: PagingState<Int, MoviesItem>): MovieKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let { movieItem ->
                movieDAO.getMovieKey(movieItem.id)
            }
        }
    }
}
