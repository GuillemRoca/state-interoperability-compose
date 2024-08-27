package io.devexpert.stateandinteroperability

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import io.devexpert.stateandinteroperability.data.Product

val LocalPriceStyle = compositionLocalOf { TextStyle() }

@Composable
fun ProductList(products: List<Product>, onProductClick: (Product) -> Unit) {
    LazyColumn {
        items(products) { product ->
            CompositionLocalProvider(
                LocalTextStyle provides TextStyle(Color.Blue),
                LocalPriceStyle provides TextStyle(Color.Red)
            ) {
                ProductItem(product, onProductClick)
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onProductClick: (Product) -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onProductClick(product) }
            .padding(16.dp)
    ) {
        Text(product.name)
        Spacer(Modifier.weight(1f))
        Text("${product.price}€", style = LocalPriceStyle.current)
    }
}