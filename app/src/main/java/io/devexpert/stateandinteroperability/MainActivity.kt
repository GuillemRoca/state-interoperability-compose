package io.devexpert.stateandinteroperability

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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

    @Composable
    fun ProductCounter() {
        var count by rememberSaveable { mutableIntStateOf(0) }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "You have clicked $count times"
            )
            Button(onClick = { count++ }) {
                Text("Click!")
            }
        }
    }

    data class ProductSort(val field: String, val ascending: Boolean)

    private val productSortListSaver = listSaver<ProductSort, Any>(
        save = { listOf(it.field, it.ascending) },
        restore = { ProductSort(it[0] as String, it[1] as Boolean) }
    )

    private val productSortMapSaver = mapSaver(
        save = { mapOf("field" to it.field, "ascending" to it.ascending) },
        restore = { ProductSort(it["field"] as String, it["ascending"] as Boolean) }
    )

    @Composable
    fun ProductSortSelector() {
        var sortOption by rememberSaveable(stateSaver = productSortMapSaver) {
            mutableStateOf(ProductSort("price", true))
        }

        Column {
            Text("Sort by: ${sortOption.field}")
            Text("Ascending: ${sortOption.ascending}")
            Button(onClick = {
                sortOption = sortOption.copy(ascending = !sortOption.ascending)
            }) {
                Text("Change sort direction")
            }
        }
    }
}