package com.example.data.network.di

import android.content.Context
import com.example.common.Constant
import com.example.data.network.ApiService
import com.example.data.network.NetworkConnectionChecker
import com.example.data.network.NoInternetInterceptor
import com.example.data.paging.MoviesPagingSource
import com.example.data.repository.MoviesRepositoryImpl
import com.example.data.repository.PagerMoviesRepositoryImpl
import com.example.data.room.MovieDAO
import com.example.data.room.MovieDataBase
import com.example.domain.repository.MoviesRepository
import com.example.domain.repository.PagerMoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun provideIsValid(): Boolean {
        return false
    }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideLoginInterceptor(isValid: Boolean): LoginInterceptor {
        return LoginInterceptor(isValid)
    }

    @Provides
    @Singleton
    fun provideNoInternetInterceptor(@ApplicationContext context: Context): NoInternetInterceptor {
        return NoInternetInterceptor(context)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        loginInterceptor: LoginInterceptor,
        noInternetInterceptor: NoInternetInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(loginInterceptor)
            .addInterceptor(noInternetInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(apiService: ApiService): MoviesRepository {
        return MoviesRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideMoviesPagingSource(
        apiService: ApiService,
        networkConnectionChecker: NetworkConnectionChecker,
        dao: MovieDAO
    ): MoviesPagingSource {
        return MoviesPagingSource(apiService, networkConnectionChecker, dao)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MovieDataBase {
        return MovieDataBase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideDAO(movieDataBase: MovieDataBase): MovieDAO {
        return movieDataBase.getMovieDAO()
    }

    @Provides
    @Singleton
    fun providePagerMoviesRepository(moviesPagingSource: MoviesPagingSource): PagerMoviesRepository {
        return PagerMoviesRepositoryImpl(moviesPagingSource)
    }

    @Provides
    @Singleton
    fun provideNetworkConnectionChecker(@ApplicationContext context: Context): NetworkConnectionChecker {
        return NetworkConnectionChecker(context)
    }
}
