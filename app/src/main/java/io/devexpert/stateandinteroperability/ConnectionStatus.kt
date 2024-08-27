package io.devexpert.stateandinteroperability

import android.net.ConnectivityManager
import android.net.Network
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.getSystemService

@Composable
fun ConnectionStatus() {
    var isConnected by remember { mutableStateOf(true) }
    val context = LocalContext.current
    DisposableEffect(context) {
        val connectivityManager = context.getSystemService<ConnectivityManager>()
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isConnected = true
            }

            override fun onLost(network: Network) {
                isConnected = false
            }
        }

        connectivityManager?.registerDefaultNetworkCallback(networkCallback)

        onDispose {
            connectivityManager?.unregisterNetworkCallback(networkCallback)
        }
    }

    Text(if (isConnected) "Connected" else "Disconnected")
}