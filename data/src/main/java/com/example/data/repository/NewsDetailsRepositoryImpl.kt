package com.example.data.repository

import com.example.data.mappers.toDomain
import com.example.data.network.ApiService
import com.example.data.network.utils.SafeApiRequest
import com.example.domain.model.NewsDetails
import com.example.domain.model.Newspapers
import com.example.domain.repository.NewsDetailRepository
import com.example.domain.repository.NewsRepository
import javax.inject.Inject

class NewsDetailsRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    NewsDetailRepository, SafeApiRequest() {
    override suspend fun getNewsDetail(id: String?): NewsDetails {
        val response = safeApiRequest {
            apiService.getNewsDetail(id) // Pass the ID to the API call
        }
        return response.toDomain()
    }
}