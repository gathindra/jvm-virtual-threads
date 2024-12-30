package dev.gathi.services

import dev.gathi.utils.ThreadUtils.randomWait

class CoroutinePricingService {

    suspend fun getPrice(sku: Int): Double {
        randomWait("Pricing Service")
        return when (sku) {
            123 -> 10.0
            456 -> 20.0
            789 -> 30.0
            else -> throw IllegalArgumentException("Product pricing not found")
        }
    }
}