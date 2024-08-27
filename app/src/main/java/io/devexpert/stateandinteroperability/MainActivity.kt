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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import io.devexpert.stateandinteroperability.data.Product
import io.devexpert.stateandinteroperability.data.sampleProducts

class MainViewModel : ViewModel() {
    var searchTerm by mutableStateOf("")
        private set
    var products by mutableStateOf(sampleProducts())
        private set

    fun onSearchChange(newSearchTerm: String) {
        searchTerm = newSearchTerm
        products = sampleProducts().filter { it.name.contains(searchTerm, true) }
    }
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Screen {
                val vm = viewModel<MainViewModel>()

                Column {

                    SearchBar(
                        searchTerm = vm.searchTerm,
                        onSearchChange = vm::onSearchChange
                    )

                    ProductList(
                        products = vm.products,
                        onProductClick = { Log.d("MainActivity", "Product clicked: $it") }
                    )
                }
            }
        }
    }
}