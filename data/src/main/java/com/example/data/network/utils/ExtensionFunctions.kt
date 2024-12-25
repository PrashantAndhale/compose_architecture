package com.example.data.network.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.getSystemService
import com.example.data.network.ConnectionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn

@ExperimentalCoroutinesApi
fun Context.observeConnectivityAsFlow() = callbackFlow {
    val connectivityManager = getSystemService<ConnectivityManager>()

    // Function to send the current network state based on the capabilities
    fun sendConnectionState() {
        // Check network capabilities for API 23+
        val network = connectivityManager?.activeNetwork
        val networkCapabilities = connectivityManager?.getNetworkCapabilities(network)
        val isConnected = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)

        // Send available or unavailable state based on network capabilities
        if (isConnected) {
            trySend(ConnectionState.Available)
        } else {
            trySend(ConnectionState.Unavailable)
        }
    }

    // Define a network request to listen for any changes in network capabilities
    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    // Define a network callback to monitor network changes
    val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            sendConnectionState()
        }

        override fun onLost(network: Network) {
            sendConnectionState()
        }
    }

    // Register the network callback to listen for connectivity changes
    connectivityManager?.registerNetworkCallback(networkRequest, networkCallback)

    // Send initial connectivity state
    sendConnectionState()

    // Clean up and unregister the network callback when the flow is collected
    awaitClose {
        connectivityManager?.unregisterNetworkCallback(networkCallback)
    }
}.distinctUntilChanged().flowOn(Dispatchers.IO)
