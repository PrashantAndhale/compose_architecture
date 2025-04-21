package com.example.data.network.di

import android.content.Context
import com.example.common.Constant
import com.example.data.network.ApiService
import com.example.data.network.NetworkConnectionChecker
import com.example.data.network.NoInternetInterceptor
import com.example.data.paging.NewsPagingSource
 import com.example.data.repository.NewsRepositoryImpl
import com.example.data.repository.PagerNewsRepositoryImpl
import com.example.data.room.NewsDAO
import com.example.data.room.NewsDataBase
 import com.example.domain.repository.NewsRepository
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
    fun provideMoviesRepository(apiService: ApiService): NewsRepository {
        return NewsRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideMoviesPagingSource(
        apiService: ApiService,
        networkConnectionChecker: NetworkConnectionChecker,
        dao: NewsDAO
    ): NewsPagingSource {
        return NewsPagingSource(apiService, networkConnectionChecker, dao)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NewsDataBase {
        return NewsDataBase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideDAO(newsDataBase: NewsDataBase): NewsDAO {
        return newsDataBase.getNewsDAO()
    }

    @Provides
    @Singleton
    fun providePagerMoviesRepository(newsPagingSource: NewsPagingSource): PagerMoviesRepository {
        return PagerNewsRepositoryImpl(newsPagingSource)
    }

    @Provides
    @Singleton
    fun provideNewsDetailRepository(newsPagingSource: NewsPagingSource): PagerMoviesRepository {
        return PagerNewsRepositoryImpl(newsPagingSource)
    }

    @Provides
    @Singleton
    fun provideNetworkConnectionChecker(@ApplicationContext context: Context): NetworkConnectionChecker {
        return NetworkConnectionChecker(context)
    }
}
