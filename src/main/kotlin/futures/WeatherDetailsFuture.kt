package dev.gathi.futures

import dev.gathi.services.WeatherServiceOne
import dev.gathi.services.WeatherServiceThree
import dev.gathi.services.WeatherServiceTwo
import java.util.concurrent.CompletableFuture

class WeatherDetailsFuture(
    private val weatherService1: WeatherServiceOne = WeatherServiceOne(),
    private val weatherService2: WeatherServiceTwo = WeatherServiceTwo(),
    private val weatherService3: WeatherServiceThree = WeatherServiceThree()
) {

    fun run(city: String): String {
        val future1 = CompletableFuture.supplyAsync { weatherService1.getWeather(city) }
        val future2 = CompletableFuture.supplyAsync { weatherService2.getWeather(city) }
        val future3 = CompletableFuture.supplyAsync { weatherService3.getWeather(city) }
        var weatherDetails = ""
            CompletableFuture.anyOf(future1, future2, future3)
            .exceptionally {
                println("Exception occurred: ${it.message}")
                throw RuntimeException("Weather Details not found")
            }
            .thenAccept { details -> weatherDetails = details.toString() }
            .join()

        return weatherDetails
    }
}