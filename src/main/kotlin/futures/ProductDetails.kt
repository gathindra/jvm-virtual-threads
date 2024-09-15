package dev.gathi.futures

import dev.gathi.services.PricingService
import dev.gathi.services.Product
import dev.gathi.services.ProductService
import java.util.concurrent.CompletableFuture

class ProductDetails(
    private val productService: ProductService = ProductService(),
    private val pricingService: PricingService = PricingService()
) {

    /**
     * This function fetches the product details and price for a given SKU using CompletableFutures.
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
        val futureProduct = CompletableFuture.supplyAsync { productService.getProduct(sku) }
        val futurePrice = CompletableFuture.supplyAsync { pricingService.getPrice(sku) }
        val productDetails = CompletableFuture.allOf(futureProduct, futurePrice)
            .exceptionally {
                println("Exception occurred: ${it.message}")
                throw RuntimeException("Product Details not found")
            }
            .thenApply {
                val product = futureProduct.join()
                val price = futurePrice.join()
                val copy = product.copy(price = price)
                println("Product Details: $copy, Price: $price")
                copy
            }
        return productDetails.join()
    }
}