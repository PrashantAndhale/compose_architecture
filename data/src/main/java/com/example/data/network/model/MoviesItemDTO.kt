package com.example.data.network.model

data class MoviesItemDTO(
    val id: Int,
    val image: String,
    val imdb_url: String,
    val movie: String,
    val rating: Double
)