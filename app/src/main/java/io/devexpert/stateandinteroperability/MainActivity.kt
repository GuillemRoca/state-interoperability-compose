package io.devexpert.stateandinteroperability

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import io.devexpert.stateandinteroperability.data.sampleProducts

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Screen {
                ProductList(
                    products = sampleProducts(),
                    onProductClick = { Log.d("MainActivity", "Product clicked: $it") }
                )
            }
        }
    }
}