package com.example.bottomnavigationandbottomsheet.shareviewmodel

import androidx.lifecycle.ViewModel
import com.example.bottomnavigationandbottomsheet.sharepreference.SharedPreferencesHelper
import com.example.domain.model.MoviesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    val preferencesHelper: SharedPreferencesHelper
) : ViewModel() {

    private val _callbackData = MutableStateFlow<MoviesItem?>(null) // Store the data in a StateFlow
    val callbackData: StateFlow<MoviesItem?> get() = _callbackData
    fun setCallbackData(moviesItem: MoviesItem?) {
        _callbackData.value = moviesItem // Set the data to trigger observers
    }

    private val _isLogin = MutableStateFlow<Boolean>(false) // Store the data in a StateFlow
    val isLogin: StateFlow<Boolean> get() = _isLogin
    fun setLogin(islogin: Boolean) {
        _isLogin.value = islogin // Set the data to trigger observers
    }


    private val _confirmExitDialogState = MutableStateFlow(false)
    val confirmExitDialogState = _confirmExitDialogState.asStateFlow()

    fun showConfirmExitDialog() {
        _confirmExitDialogState.value = true
    }

    fun dismissConfirmExitDialog() {
        _confirmExitDialogState.value = false
    }


}
