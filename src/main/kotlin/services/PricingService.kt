package dev.gathi.services

import dev.gathi.utils.ThreadUtils.randomThreadWait

class PricingService {

    fun getPrice(sku: Int): Double {
        Thread.currentThread().randomThreadWait("Pricing Service")
        return when (sku) {
            123 -> 10.0
            456 -> 20.0
            789 -> 30.0
            else -> throw IllegalArgumentException("Product pricing not found")
        }
    }
}