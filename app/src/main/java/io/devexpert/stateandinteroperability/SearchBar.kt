package io.devexpert.stateandinteroperability

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchBar(searchTerm: String, onSearchChange: (String) -> Unit) {
    TextField(
        value = searchTerm,
        onValueChange = onSearchChange,
        label = { Text("Search Products") },
        modifier = Modifier.fillMaxWidth()
    )
}