package dev.gathi.virtual

import dev.gathi.services.PricingService
import dev.gathi.services.Product
import dev.gathi.services.ProductService
import java.util.concurrent.StructuredTaskScope

class ProductDetailsVirtual(
    private val productService: ProductService = ProductService(),
    private val pricingService: PricingService = PricingService()
) {


    /**
     * This function fetches the product details and price for a given SKU
     * using `Structured Concurrency` with virtual threads.
     *
     * It runs two tasks concurrently: one to fetch the product details from the ProductService,
     * and another to fetch the product price from the PricingService.
     * If either of these tasks fails, it throws a RuntimeException.
     *
     * @param sku The SKU of the product to fetch details for.
     * @return A Product object containing the product details and price.
     * @throws RuntimeException If the product details or price could not be fetched.
     */
    fun run(sku: Int): Product {
        try {
            StructuredTaskScope.ShutdownOnFailure().use { scope ->
                val productTask = scope.fork { productService.getProduct(sku) }
                val priceTask = scope.fork { pricingService.getPrice(sku) }
                scope.join().throwIfFailed()
                val product = productTask.get()
                val price = priceTask.get()
                val copy = product.copy(price = price)
                println("Product Details: $copy, Price: $price")
                return copy

            }
        } catch (e: Exception) {
            println("Exception occurred: ${e.message}")
            throw RuntimeException("Product Details not found")
        }
    }
}