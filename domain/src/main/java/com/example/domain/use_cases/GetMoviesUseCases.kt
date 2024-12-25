package com.example.domain.use_cases

import com.example.common.Resource
import com.example.common.Resource.Error
import com.example.common.Resource.Loading
import com.example.common.Resource.Success
import com.example.domain.model.MoviesItem
import com.example.domain.repository.MoviesRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMoviesUseCases @Inject constructor(private val getMoviesRepository: MoviesRepository) {
    fun getMovies(): Flow<Resource<List<MoviesItem>>> = flow {
        emit(Loading())
        try {
            val response = getMoviesRepository.getMovies()
            emit(Success(data = response))
        } catch (e: Exception) {
            emit(Error(e.message))
        }
    }

    fun getMoviesDetail(id: Int): Flow<Resource<MoviesItem>> =
        flow {
            emit(Loading())
            try {
                val response = getMoviesRepository.getMoviesDetail(id)
                emit(Success(data = response))
            } catch (e: Exception) {
                emit(Error(e.message))
            }
        }
}