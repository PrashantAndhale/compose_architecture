package com.example.news.screens.news

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.common.Resource
import com.example.data.network.ConnectionState
import com.example.data.network.utils.observeConnectivityAsFlow
import com.example.data.repository.PagerNewsRepositoryImpl
import com.example.domain.model.NewsDetails
import com.example.domain.model.Newspapers
import com.example.domain.use_cases.GetNewsDetailUseCases
import com.example.domain.use_cases.GetNewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: PagerNewsRepositoryImpl,
    private val cases: GetNewsUseCases,
    private val casesDetails: GetNewsDetailUseCases,
    private val application: Application
) : ViewModel() {

    private val _moviesFlow = MutableStateFlow<PagingData<Newspapers>>(PagingData.empty())
    val moviesFlow: StateFlow<PagingData<Newspapers>> = _moviesFlow

    private val _movies = MutableStateFlow<Resource<List<Newspapers>>>(Resource.Loading())
    val movies: StateFlow<Resource<List<Newspapers>>> = _movies

    private val _newsdetail = MutableStateFlow<Resource<NewsDetails>>(Resource.Loading())
    val newsdetail: StateFlow<Resource<NewsDetails>> = _newsdetail

    val _isConnected = MutableStateFlow<Boolean?>(true)
    val isConnected: StateFlow<Boolean?> = _isConnected.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val networkStateFlow: Flow<ConnectionState> = application.observeConnectivityAsFlow()

    init {
        viewModelScope.launch {
            networkStateFlow.collect { connectionState ->
                when (connectionState) {
                    ConnectionState.Available -> {
                        _isConnected.value = true
                        getNews()
                    }

                    ConnectionState.Unavailable -> {
                        getNews()
                        _isConnected.value = false
                    }
                }
            }
        }
    }

    fun clearErrorMessage() {
        _isConnected.value = false
    }

    fun getNews() {
        viewModelScope.launch {
            cases.getNews().collect { resource ->
                _movies.value = resource
            }
        }
    }

    fun getNewsDetail(id: String?) {
        viewModelScope.launch {
            casesDetails.getNewsDetail(id).collect { resource ->
                _newsdetail.value = resource
            }
        }
    }
}

