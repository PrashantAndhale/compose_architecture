package com.example.data.repository

import com.example.data.mappers.toDomain
import com.example.data.network.ApiService
import com.example.data.network.model.MoviesRequest
import com.example.data.network.utils.SafeApiRequest
import com.example.domain.model.MoviesItem
import com.example.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    MoviesRepository, SafeApiRequest() {

    override suspend fun getMovies(): List<MoviesItem> {
        val response = safeApiRequest {
            apiService.getMovies()
        }
        return response.toDomain() ?: emptyList()
    }

    override suspend fun getMoviesDetail( id: Int): MoviesItem {
         val response = safeApiRequest {
            apiService.getMoviesDetails(id)
        }
        return response.toDomain()
    }

}