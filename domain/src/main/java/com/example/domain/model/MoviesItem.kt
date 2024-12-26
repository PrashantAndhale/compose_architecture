package com.example.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class MoviesItem (
        @PrimaryKey(autoGenerate = false)
        val id: Int,
        val image: String,
        val imdb_url: String,
        val movie: String,
        val rating: Double
)