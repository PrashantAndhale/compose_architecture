package com.example.domain.di

import com.example.domain.repository.MoviesRepository
import com.example.domain.use_cases.GetMoviesUseCases
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
    fun provideGetMoviesUseCases(repository: MoviesRepository): GetMoviesUseCases {
        return GetMoviesUseCases(repository)
    }
}