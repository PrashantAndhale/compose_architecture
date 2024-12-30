package com.example.bottomnavigationandbottomsheet.navigation.drawernavigation.movies

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.common.Resource
import com.example.data.network.ConnectionState
import com.example.data.network.NoInternetConnectionException
import com.example.data.network.utils.observeConnectivityAsFlow
import com.example.data.repository.PagerMoviesRepositoryImpl
import com.example.domain.model.MoviesItem
import com.example.domain.use_cases.GetMoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: PagerMoviesRepositoryImpl,
    private val cases: GetMoviesUseCases,
    private val application: Application
) : ViewModel() {

    private val _moviesFlow = MutableStateFlow<PagingData<MoviesItem>>(PagingData.empty())
    val moviesFlow: StateFlow<PagingData<MoviesItem>> = _moviesFlow

    private val _movies = MutableStateFlow<Resource<List<MoviesItem>>>(Resource.Loading())
    val movies: StateFlow<Resource<List<MoviesItem>>> = _movies

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
                        getMovies()
                        // fetchMovies()

                    }

                    ConnectionState.Unavailable -> {
                        getMovies()
                        _isConnected.value = false
                    }
                }
            }
        }
    }

    fun fetchMovies() {
        viewModelScope.launch {
            repository.getPagerMovies()
                .cachedIn(viewModelScope)
                .onStart {
                }
                .catch { exception ->
                    val message = if (exception is NoInternetConnectionException) {
                        "No Internet Connection!"
                    } else {
                        exception.message.toString()
                    }
                }
                .collectLatest {
                    _moviesFlow.value = it
                }
        }
    }

    fun clearErrorMessage() {
        _isConnected.value = false
    }


    fun getMovies() {
        viewModelScope.launch {
            cases.getMovies().collect { resource ->
                _movies.value = resource
            }
        }
    }

    fun getMoviesDetail() {
        viewModelScope.launch {
            cases.getMovies().collect { resource ->
                _movies.value = resource
            }
        }
    }

}

