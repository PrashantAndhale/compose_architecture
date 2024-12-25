package com.example.data.network

import com.example.data.network.model.MoviesDTO
import com.example.data.network.model.MoviesItemDTO
import com.example.data.network.model.MoviesRequest
import com.example.domain.model.MoviesItem
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    @GET("movies")
    suspend fun getMovies(): Response<MoviesDTO>

    @GET("movies/{id}")
    suspend fun getMoviesDetails(@Path("id") id: Int): Response<MoviesItemDTO>
}