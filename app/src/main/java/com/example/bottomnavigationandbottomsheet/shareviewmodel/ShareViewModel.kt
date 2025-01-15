package com.example.bottomnavigationandbottomsheet.shareviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bottomnavigationandbottomsheet.sharepreference.SharedPreferencesHelper
import com.example.domain.model.MoviesItem
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.isGranted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class SharedViewModel @Inject constructor(
    private val preferencesHelper: SharedPreferencesHelper // Use private to encapsulate
) : ViewModel() {

    // Callback Data for MoviesItem
    private val _callbackData = MutableStateFlow<MoviesItem?>(null)
    val callbackData: StateFlow<MoviesItem?> get() = _callbackData

    fun setCallbackData(moviesItem: MoviesItem?) {
        _callbackData.value = moviesItem
    }

    // Login State
   open val _isLogin = MutableStateFlow(false)
   open val isLogin: StateFlow<Boolean> get() = _isLogin

   open fun setLogin(isLogin: Boolean) {
        _isLogin.value = isLogin
    }

    // Confirmation Dialog State
    private val _confirmExitDialogState = MutableStateFlow(false)
    val confirmExitDialogState: StateFlow<Boolean> get() = _confirmExitDialogState.asStateFlow()

    fun showConfirmExitDialog() {
        _confirmExitDialogState.value = true
    }

    fun dismissConfirmExitDialog() {
        _confirmExitDialogState.value = false
    }

    // Permissions Handling
    @OptIn(ExperimentalPermissionsApi::class)
    private val _permissionsState = MutableStateFlow<MultiplePermissionsState?>(null)
    @OptIn(ExperimentalPermissionsApi::class)
    val permissionsState: StateFlow<MultiplePermissionsState?> get() = _permissionsState.asStateFlow()

    private val _permissionsGranted = MutableStateFlow(false)
    val permissionsGranted: StateFlow<Boolean> get() = _permissionsGranted.asStateFlow()

    @OptIn(ExperimentalPermissionsApi::class)
    fun initializePermissionsState(state: MultiplePermissionsState) {
        _permissionsState.value = state
        checkPermissions()
    }

    @OptIn(ExperimentalPermissionsApi::class)
    fun requestPermissions() {
        viewModelScope.launch {
            _permissionsState.value?.launchMultiplePermissionRequest()
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    fun arePermissionsGranted(): Boolean {
        return _permissionsState.value?.permissions?.all { it.status.isGranted } == true
    }

    @OptIn(ExperimentalPermissionsApi::class)
    fun checkPermissions() {
        _permissionsGranted.value =
            _permissionsState.value?.permissions?.all { it.status.isGranted } == true
    }
}
