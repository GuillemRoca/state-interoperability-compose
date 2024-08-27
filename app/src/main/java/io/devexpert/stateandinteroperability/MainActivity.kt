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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Screen {
                ProductCounter()
            }
        }
    }
}

@Composable
fun ProductCounter() {
    var count by remember { mutableIntStateOf(0) }

    Column {
        Text("Productos vistos: $count")
        Button(onClick = { count++ }) {
            Text("Ver siguiente producto")
        }
    }
}