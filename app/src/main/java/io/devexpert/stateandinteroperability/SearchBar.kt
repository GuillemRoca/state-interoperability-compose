package io.devexpert.stateandinteroperability

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@OptIn(FlowPreview::class)
@Composable
fun SearchBar(searchTerm: String, onSearchChange: (String) -> Unit) {
    var recomposeCount by remember { mutableStateOf(0) }
    SideEffect {
        recomposeCount++
        Log.d("SearchBar", "Recompose count: $recomposeCount")
    }
    var localSearchTerm by remember { mutableStateOf(searchTerm) }

    LaunchedEffect(searchTerm) {
        snapshotFlow { localSearchTerm }
            .debounce(500)
            .filter { it.length > 2 }
            .distinctUntilChanged()
            .collect { newSearchTerm ->
                onSearchChange(newSearchTerm)
            }
    }

    TextField(
        value = localSearchTerm,
        onValueChange = { localSearchTerm = it },
        label = { Text("Search Products") },
        modifier = Modifier.fillMaxWidth()
    )
}