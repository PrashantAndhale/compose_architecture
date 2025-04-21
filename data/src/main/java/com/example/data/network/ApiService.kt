package com.example.data.network

import com.example.data.network.model.NewsDetailsDTO
import com.example.data.network.model.NewsPaperDTO
import com.example.data.network.model.NewspapersDTO
import com.example.domain.model.NewsDetails
import com.example.domain.model.Newspapers
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    @GET("newspapers.json")
    suspend fun getNews(): Response<NewsPaperDTO>

    @GET("lccn/{id}.json")
    suspend fun getNewsDetail(
        @Path("id") id: String? // Add the id parameter as a path parameter
    ): Response<NewsDetailsDTO>
}

