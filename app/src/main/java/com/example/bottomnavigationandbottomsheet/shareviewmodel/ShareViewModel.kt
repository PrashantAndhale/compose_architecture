package com.example.bottomnavigationandbottomsheet.shareviewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.model.MoviesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    private val _callbackData = MutableStateFlow<MoviesItem?>(null) // Store the data in a StateFlow
    val callbackData: StateFlow<MoviesItem?> get() = _callbackData
    fun setCallbackData(moviesItem: MoviesItem) {
        _callbackData.value = moviesItem // Set the data to trigger observers
    }
}
