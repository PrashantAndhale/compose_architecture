package com.example.domain.di

import com.example.domain.repository.NewsDetailRepository
import com.example.domain.repository.NewsRepository
import com.example.domain.use_cases.GetNewsDetailUseCases
import com.example.domain.use_cases.GetNewsUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {
    @Provides
    @Singleton
    fun provideGetNewsUseCases(repository: NewsRepository): GetNewsUseCases {
        return GetNewsUseCases(repository)
    }

    @Provides
    @Singleton
    fun provideGetNewsDetailsUseCases(repository: NewsDetailRepository): GetNewsDetailUseCases {
        return GetNewsDetailUseCases(repository)
    }
}
