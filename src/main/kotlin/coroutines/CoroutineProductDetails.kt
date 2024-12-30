package dev.gathi.coroutines

import dev.gathi.services.CoroutinePricingService
import dev.gathi.services.CoroutineProductService
import dev.gathi.services.Product
import dev.gathi.utils.KotlinCoroutineDispatcherProvider.LOOM
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

class CoroutineProductDetails(
    private val productService: CoroutineProductService = CoroutineProductService(),
    private val pricingService: CoroutinePricingService = CoroutinePricingService()
) {
    /**
     * This function fetches the product details and price for a given SKU
     * using `Structured Concurrency` with coroutines.
     * This function is a coroutine that runs on a virtual thread.
     * Here we are using the custom LOOM dispatcher to run the coroutine on a virtual thread
     * RunBlocking coroutine builder is used to block the executing virtual thread until the coroutine completes
     *
     * It runs two tasks concurrently: one to fetch the product details from the ProductService,
     * and another to fetch the product price from the PricingService.
     * If either of these tasks fails, it throws a RuntimeException.
     *
     * @param sku The SKU of the product to fetch details for.
     * @return A Product object containing the product details and price.
     * @throws RuntimeException If the product details or price could not be fetched.
     */
    fun run(sku: Int): Product = runBlocking(Dispatchers.LOOM) {
        println("Executing coroutine Product Details in ${Thread.currentThread()}")

        val deferredProduct: Deferred<Product> = async { productService.getProduct(sku) }
        val deferredPrice: Deferred<Double> = async { pricingService.getPrice(sku) }

        return@runBlocking calculateProductPrice(deferredProduct, deferredPrice)
    }

    private suspend fun calculateProductPrice(deferredProduct: Deferred<Product>, deferredPrice: Deferred<Double>): Product {
        println("Calculating product price in ${Thread.currentThread()}")
        try {
            val results = awaitAll(deferredProduct, deferredPrice)
            val product = results[0] as Product
            val price = results[1] as Double
            return product.copy(price = price)
        } catch (e: Exception) {
            println("Exception occurred: ${e.message}")
            if (deferredProduct.isCancelled) {
                throw RuntimeException("Product Details not found")
            } else if (deferredPrice.isCancelled) {
                throw RuntimeException("Product Price not found")
            }
            else {
                println("Exception occurred: ${e.message}")
                throw e
            }
        }

    }
}












