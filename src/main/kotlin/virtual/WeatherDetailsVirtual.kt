package dev.gathi.virtual

import dev.gathi.services.WeatherServiceOne
import dev.gathi.services.WeatherServiceThree
import dev.gathi.services.WeatherServiceTwo
import java.util.concurrent.StructuredTaskScope

class WeatherDetailsVirtual (
    private val weatherServiceOne: WeatherServiceOne = WeatherServiceOne(),
    private val weatherServiceTwo: WeatherServiceTwo = WeatherServiceTwo(),
    private val weatherServiceThree: WeatherServiceThree = WeatherServiceThree()
) {

    fun run(city: String): String {
        return try {
            StructuredTaskScope.ShutdownOnSuccess<String>().use { scope ->
                scope.fork { weatherServiceTwo.getWeather(city) }
                scope.fork { weatherServiceOne.getWeather(city) }
                scope.fork { weatherServiceThree.getWeather(city) }
                scope.join().result()
            }
        } catch (e: Exception) {
            println("Exception occurred: ${e.message}")
            throw RuntimeException("Weather Details not found")
        }
    }
}