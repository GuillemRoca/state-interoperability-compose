package io.devexpert.stateandinteroperability

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun SearchBar(searchTerm: String, onSearchChange: (String) -> Unit) {

    var recomposeCount by remember { mutableIntStateOf(0) }

    SideEffect {
        recomposeCount++
        Log.d("Compose", "Recomposition count: $recomposeCount")
    }

    TextField(
        value = searchTerm,
        onValueChange = onSearchChange,
        label = { Text("Search Products") },
        modifier = Modifier.fillMaxWidth()
    )
}