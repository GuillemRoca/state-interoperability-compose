package io.devexpert.stateandinteroperability

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.devexpert.stateandinteroperability.data.Product
import io.devexpert.stateandinteroperability.data.sampleProducts

class MainState(searchTerm: String, products: List<Product>) {
    var searchTerm by mutableStateOf(searchTerm)
        private set
    var products by mutableStateOf(products)
        private set

    fun onSearchChange(newSearchTerm: String) {
        searchTerm = newSearchTerm
        products = sampleProducts().filter { it.name.contains(searchTerm, true) }
    }
}

@Composable
fun rememberMainState(
    searchTerm: String = "",
    products: List<Product> = sampleProducts()
): MainState {
    return remember { MainState(searchTerm, products) }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Screen {
                val state = rememberMainState()

                Column {

                    SearchBar(
                        searchTerm = state.searchTerm,
                        onSearchChange = state::onSearchChange
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