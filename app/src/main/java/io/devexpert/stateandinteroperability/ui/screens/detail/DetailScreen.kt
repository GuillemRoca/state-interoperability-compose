package io.devexpert.stateandinteroperability.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.fragment.compose.AndroidFragment
import androidx.fragment.compose.rememberFragmentState
import io.devexpert.stateandinteroperability.Screen

@Composable
fun DetailScreen() {
    Screen {
        val fragmentState = rememberFragmentState()
        Column {
            var contentVisible by remember { mutableStateOf(true) }
            Button(onClick = { contentVisible = !contentVisible }) {
                Text(if (contentVisible) "Hide" else "Show")
            }
            if (contentVisible) {
                AndroidFragment<DetailFragment>(fragmentState = fragmentState)
            }
        }
    }
}