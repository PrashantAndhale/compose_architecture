package com.example.bottomnavigationandbottomsheet.utils

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.bottomnavigationandbottomsheet.shareviewmodel.SharedViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HandledPermissions(viewModel: SharedViewModel = hiltViewModel()) {
    // Initialize permissionsState in ViewModel
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO
        )
    )
    // Initialize the ViewModel with the permissions state
    LaunchedEffect(permissionsState) {
        viewModel.initializePermissionsState(permissionsState)
    }

    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.requestPermissions()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    permissionsState.permissions.forEach { perm ->
        when (perm.permission) {
            Manifest.permission.CAMERA -> {
                when {
                    perm.status.isGranted -> {
                        viewModel.checkPermissions()
                    }

                    perm.status.shouldShowRationale -> {
                        //  Log.d("Permission-->", "Camera Permission Required")
                    }

                    perm.isPermanentlyDenied() -> {/*  Log.d(
                              "Permission-->",
                              "Camera Permission was permanently denied. You can go to app settings and enable it."
                          )*/
                    }
                }
            }

            Manifest.permission.RECORD_AUDIO -> {
                when {
                    perm.status.isGranted -> {
                        // Log.d("Permission-->", "RECORD_AUDIO Permission Granted")
                    }

                    perm.status.shouldShowRationale -> {
                        // Log.d("Permission-->", "RECORD_AUDIO Permission Required")
                    }

                    perm.isPermanentlyDenied() -> {/*   Log.d(
                               "Permission-->",
                               "RECORD_AUDIO Permission was permanently denied. You can go to app settings and enable it."
                           )*/
                    }
                }
            }
        }
    }
}

@ExperimentalPermissionsApi
fun PermissionState.isPermanentlyDenied(): Boolean {
    return !status.isGranted && !status.shouldShowRationale
}

