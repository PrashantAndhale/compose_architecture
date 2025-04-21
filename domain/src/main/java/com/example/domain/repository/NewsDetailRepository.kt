package com.example.domain.repository

import com.example.domain.model.NewsDetails
import com.example.domain.model.Newspapers


interface NewsDetailRepository {
    suspend fun getNewsDetail(id: String?): NewsDetails
}