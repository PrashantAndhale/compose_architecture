package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.paging.NewsPagingSource
import com.example.domain.model.NewsDetails
import com.example.domain.model.Newspapers
import com.example.domain.repository.PagerMoviesRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow


class PagerNewsRepositoryImpl @Inject constructor(
    private val pagingSource: NewsPagingSource,
) : PagerMoviesRepository {
    override fun getPagerNews(): Flow<PagingData<Newspapers>> {
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