package io.devexpert.stateandinteroperability.data

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val description: String
)

fun sampleProducts(): List<Product> {
    return listOf(
        Product(
            id = "1",
            name = "Smartphone",
            price = 699.99,
            description = "A high-end smartphone with a great camera and display."
        ),
        Product(
            id = "2",
            name = "Laptop",
            price = 1299.99,
            description = "A powerful laptop for professionals and gamers."
        ),
        Product(
            id = "3",
            name = "Headphones",
            price = 199.99,
            description = "Noise-cancelling headphones with superior sound quality."
        ),
        Product(
            id = "4",
            name = "Smartwatch",
            price = 249.99,
            description = "A smartwatch with fitness tracking and notifications."
        ),
        Product(
            id = "5",
            name = "Tablet",
            price = 499.99,
            description = "A lightweight tablet perfect for reading and browsing."
        )
    )
}