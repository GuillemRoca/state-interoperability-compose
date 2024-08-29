package io.devexpert.stateandinteroperability.ui.screens.home

import androidx.lifecycle.ViewModel
import io.devexpert.stateandinteroperability.data.Product
import io.devexpert.stateandinteroperability.data.sampleProducts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
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