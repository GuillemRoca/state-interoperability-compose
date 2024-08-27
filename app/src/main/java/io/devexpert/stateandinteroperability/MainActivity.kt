package io.devexpert.stateandinteroperability

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
            Screen {
                val vm = viewModel<MainViewModel>()
                val state by vm.state.collectAsState()

                Column {

                    SearchBar(
                        searchTerm = state.searchTerm,
                        onSearchChange = vm::onSearchChange
                    )

                    ProductList(
                        products = state.products,
                        onProductClick = { Log.d("MainActivity", "Product clicked: $it") }
                    )
                }
            }
        }
    }
}