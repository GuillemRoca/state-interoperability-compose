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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.devexpert.stateandinteroperability.data.sampleProducts
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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

    @Composable
    fun rememberMainState() = remember { MainState() }

    class MainState {
        var searchTerm by mutableStateOf("")
        val products
            get() = sampleProducts().filter { it.name.contains(searchTerm, true) }

        fun onSearchChange(searchTerm: String) {
            this.searchTerm = searchTerm
        }
    }

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

                Column {
                    val state = rememberMainState()

                    SearchBar(
                        searchTerm = state.searchTerm,
                        onSearchChange = { state.onSearchChange(it) }
                    )
                    val scope = rememberCoroutineScope()
                    ProductList(
                        products = state.products,
                        onProductClick = {
                            scope.launch {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState.showSnackbar("Product clicked: ${it.name}")
                            }
                        }
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