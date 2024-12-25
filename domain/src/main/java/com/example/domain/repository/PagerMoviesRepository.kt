package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.MoviesItem
import kotlinx.coroutines.flow.Flow

interface PagerMoviesRepository {
    fun getPagerMovies(): Flow<PagingData<MoviesItem>>
}