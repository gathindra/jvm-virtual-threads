package dev.gathi.services

import dev.gathi.services.ProductService.Companion.products
import dev.gathi.utils.ThreadUtils.randomWait

class CoroutineProductService {

    suspend fun getProduct(sku: Int): Product {
        randomWait("Product Service")
        return products[sku] ?: throw IllegalArgumentException("Product not found")
    }
}