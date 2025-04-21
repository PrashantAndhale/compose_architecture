package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.NewsDetails
import com.example.domain.model.Newspapers
import kotlinx.coroutines.flow.Flow

interface PagerMoviesRepository {
    fun getPagerNews(): Flow<PagingData<Newspapers>>
 }