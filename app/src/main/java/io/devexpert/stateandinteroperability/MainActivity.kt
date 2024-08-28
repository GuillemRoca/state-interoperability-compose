package io.devexpert.stateandinteroperability

import android.os.Bundle
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Screen {
                ProductSortSelector()
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

    val productSortSaver = listSaver<ProductSort, Any>(
        save = { listOf(it.field, it.ascending) },
        restore = { ProductSort(it[0] as String, it[1] as Boolean) }
    )

    @Composable
    fun ProductSortSelector() {
        var sortOption by rememberSaveable(stateSaver = productSortSaver) {
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