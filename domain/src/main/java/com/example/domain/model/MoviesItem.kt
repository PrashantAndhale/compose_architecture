package com.example.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoviesItem (
        @PrimaryKey(autoGenerate = false)
        val id: Int,
        val image: String,
        val imdb_url: String,
        val movie: String,
        val rating: Double
)