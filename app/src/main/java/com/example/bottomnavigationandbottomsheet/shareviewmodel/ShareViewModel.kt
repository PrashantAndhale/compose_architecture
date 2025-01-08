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


    /* <------ Confirmation Dialog Part----------->*/

    private val _confirmExitDialogState = MutableStateFlow(false)
    val confirmExitDialogState = _confirmExitDialogState.asStateFlow()

    fun showConfirmExitDialog() {
        _confirmExitDialogState.value = true
    }

    fun dismissConfirmExitDialog() {
        _confirmExitDialogState.value = false
    }
    /* <------ Confirmation Dialog Part----------->*/



    /* <------ Permission Part----------->*/

    // Mutable state flow to hold the permissions state
    @OptIn(ExperimentalPermissionsApi::class)
    val _permissionsState = MutableStateFlow<MultiplePermissionsState?>(null)

    @OptIn(ExperimentalPermissionsApi::class)
    val permissionsState: StateFlow<MultiplePermissionsState?> get() = _permissionsState.asStateFlow()

    // Mutable state flow to track if permissions are granted
    private val _permissionsGranted = MutableStateFlow(false)
    val permissionsGranted: StateFlow<Boolean> get() = _permissionsGranted.asStateFlow()

    // Function to initialize permissions state
    @OptIn(ExperimentalPermissionsApi::class)
    fun initializePermissionsState(state: MultiplePermissionsState) {
        _permissionsState.value = state
        checkPermissions()
    }

    // Function to request permissions
    @OptIn(ExperimentalPermissionsApi::class)
    fun requestPermissions() {
        viewModelScope.launch {
            _permissionsState.value?.launchMultiplePermissionRequest()
        }
    }

    // Function to check if all permissions are granted
    @OptIn(ExperimentalPermissionsApi::class)
    fun arePermissionsGranted(): Boolean {
        val state = _permissionsState.value
        return state?.permissions?.all { it.status.isGranted } == true
    }

    // Function to check if all permissions are granted
    @OptIn(ExperimentalPermissionsApi::class)
    fun checkPermissions() {
        val state = _permissionsState.value
        _permissionsGranted.value = state?.permissions?.all { it.status.isGranted } == true
    }

    /* <------ Permission Part----------->*/

}
