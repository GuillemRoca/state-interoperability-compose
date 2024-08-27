package io.devexpert.stateandinteroperability

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import io.devexpert.stateandinteroperability.data.Product
import io.devexpert.stateandinteroperability.data.sampleProducts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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

    fun onProductClick(product: Product) {
        _state.value = _state.value.copy(message = "Product clicked: ${product.name}")
    }

    fun onMessageShown() {
        _state.value = _state.value.copy(message = null)
    }

    data class UiState(
        val searchTerm: String = "",
        val products: List<Product> = sampleProducts(),
        val message: String? = null
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            Screen(
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
            ) {
                val vm = viewModel<MainViewModel>()
                val state by vm.state.collectAsState()

                LaunchedEffect(state.message) {
                    state.message?.let { snackbarHostState.showSnackbar(it) }
                    vm.onMessageShown()
                }

                Column {

                    SearchBar(
                        searchTerm = state.searchTerm,
                        onSearchChange = vm::onSearchChange
                    )

                    ProductList(
                        products = state.products,
                        onProductClick = vm::onProductClick
                    )
                }
            }
        }
    }
}