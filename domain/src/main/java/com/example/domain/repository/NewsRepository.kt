package com.example.domain.repository

import com.example.domain.model.NewsDetails
import com.example.domain.model.Newspapers


interface NewsRepository {
    suspend fun getNews(): List<Newspapers>
}