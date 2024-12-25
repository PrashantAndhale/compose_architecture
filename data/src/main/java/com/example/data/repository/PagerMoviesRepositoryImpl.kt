package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.paging.MoviesPagingSource
import com.example.domain.model.MoviesItem
import com.example.domain.repository.PagerMoviesRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow


class PagerMoviesRepositoryImpl @Inject constructor(
    private val pagingSource: MoviesPagingSource
) : PagerMoviesRepository {
    override fun getPagerMovies(): Flow<PagingData<MoviesItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 3,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { pagingSource }
        ).flow
    }
}