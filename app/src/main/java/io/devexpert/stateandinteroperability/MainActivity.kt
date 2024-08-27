package io.devexpert.stateandinteroperability

import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.getSystemService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import io.devexpert.stateandinteroperability.data.Product
import io.devexpert.stateandinteroperability.data.sampleProducts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun onSearchChange(newSearchTerm: String) {
        _state.value = UiState(
            searchTerm = newSearchTerm,
            products = sampleProducts().filter {
                it.name.contains(newSearchTerm, ignoreCase = true)
            }
        )
    }

    data class UiState(
        val searchTerm: String = "",
        val products: List<Product> = sampleProducts()
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()
            Screen(
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
            ) {
                val vm = viewModel<MainViewModel>()
                val state by vm.state.collectAsState()

                Column {
                    ConnectionStatus()
                    SearchBar(
                        searchTerm = state.searchTerm,
                        onSearchChange = vm::onSearchChange
                    )

                    ProductList(
                        products = state.products,
                        onProductClick = { product ->
                            scope.launch {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState.showSnackbar(
                                    "Product clicked: ${product.name}"
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

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