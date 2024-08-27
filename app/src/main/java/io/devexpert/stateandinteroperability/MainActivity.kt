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
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

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
}

data class ProductSort(val field: String, val ascending: Boolean)

val ProductSortListSaver = mapSaver(
    save = {
        mapOf(
            "field" to it.field,
            "ascending" to it.ascending
        )
    },
    restore = { ProductSort(it["field"] as String, it["ascending"] as Boolean) }
)

@Composable
fun ProductSortSelector() {
    var sortOption by rememberSaveable(stateSaver = ProductSortListSaver) {
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