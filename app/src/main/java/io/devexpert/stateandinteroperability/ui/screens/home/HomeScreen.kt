package io.devexpert.stateandinteroperability.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import io.devexpert.stateandinteroperability.ConnectionStatus
import io.devexpert.stateandinteroperability.ProductList
import io.devexpert.stateandinteroperability.Screen
import io.devexpert.stateandinteroperability.SearchBar

@Composable
fun HomeScreen(vm: HomeViewModel = viewModel(), onProductClick: (String) -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    Screen(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        val state by vm.state.collectAsState()

        Column {
            ConnectionStatus()
            SearchBar(
                searchTerm = state.searchTerm,
                onSearchChange = vm::onSearchChange
            )

            ProductList(
                products = state.products,
                onProductClick = { onProductClick(it.name) }
            )
        }
    }
}