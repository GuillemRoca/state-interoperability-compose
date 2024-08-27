package io.devexpert.stateandinteroperability

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.devexpert.stateandinteroperability.data.sampleProducts

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Screen {
                Column {
                    var searchTerm by remember { mutableStateOf("") }
                    val products = remember(searchTerm) {
                        sampleProducts().filter { it.name.contains(searchTerm, true) }
                    }

                    SearchBar(searchTerm = searchTerm, onSearchChange = { searchTerm = it })

                    ProductList(
                        products = products,
                        onProductClick = { Log.d("MainActivity", "Product clicked: $it") }
                    )
                }
            }
        }
    }
}