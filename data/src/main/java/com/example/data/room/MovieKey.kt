package com.example.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieKey(
    @PrimaryKey(autoGenerate = false)
    val id: Int, val prev: Int?, val next: Int?
)