package com.example.domain.repository

import com.example.domain.model.MoviesItem


interface MoviesRepository {
    suspend fun getMovies(): List<MoviesItem>
    suspend fun getMoviesDetail(anotherField: Int):MoviesItem
}