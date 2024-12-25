package com.example.bottomnavigationandbottomsheet.screens

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.data.network.ConnectionState
import com.example.data.network.utils.observeConnectivityAsFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest


open class BaseActivity : ComponentActivity() {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Composable
    fun connection_state(): ConnectionState {
        var connectionState by remember { mutableStateOf(ConnectionState.Unavailable) }
        var context = LocalContext.current.applicationContext
        LaunchedEffect(context) {
            context.observeConnectivityAsFlow().collectLatest {
                connectionState = it
            }
        }
        return connectionState
    }
}