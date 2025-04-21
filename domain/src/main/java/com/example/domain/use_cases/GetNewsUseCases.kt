package com.example.domain.use_cases

import com.example.common.Resource
import com.example.common.Resource.Error
import com.example.common.Resource.Loading
import com.example.common.Resource.Success
import com.example.domain.model.NewsDetails
import com.example.domain.model.Newspapers
import com.example.domain.repository.NewsDetailRepository
import com.example.domain.repository.NewsRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNewsUseCases @Inject constructor(
    private val getNewsRepository: NewsRepository,
 ) {
    fun getNews(): Flow<Resource<List<Newspapers>>> = flow {
        emit(Loading())
        try {
            val response = getNewsRepository.getNews()
            emit(Success(data = response))
        } catch (e: Exception) {
            emit(Error(e.message))
        }
    }

  /*  fun getNewsDetail(id: String?): Flow<Resource<NewsDetails>> = flow {
        emit(Loading())
        try {
            val response = getNewsDetailRepository.getNewsDetail(id)
            emit(Success(data = response))
        } catch (e: Exception) {
            emit(Error(e.message))
        }
    }*/
}