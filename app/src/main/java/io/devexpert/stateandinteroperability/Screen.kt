package io.devexpert.stateandinteroperability

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.devexpert.stateandinteroperability.ui.theme.StateAndInteroperabilityTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(
    snackbarHost: @Composable () -> Unit = { },
    content: @Composable () -> Unit
) {
    StateAndInteroperabilityTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .imePadding(),
            topBar = { TopAppBar(title = { Text("State and Interoperability") }) },
            snackbarHost = snackbarHost
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }
    }
}