package dev.gathi.services

import dev.gathi.utils.ThreadUtils.randomThreadWait

class ProductService {

    companion object {
        val products: Map<Int, Product>
            get() =  mapOf(
                123 to Product(123, "123", "Product 1", 10.0),
                456 to Product(456, "456", "Product 2", 20.0),
                789 to Product(789, "789", "Product 3", 30.0)
            )
    }

    fun getProduct(sku: Int): Product {
        Thread.currentThread().randomThreadWait("Product Service")
        return products[sku] ?: throw IllegalArgumentException("Product not found")
    }
}

data class Product(
    val sku: Int,
    val upc: String,
    val name: String,
    val price: Double = 0.0
)

