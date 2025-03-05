package dev.gathi.services

import dev.gathi.utils.ThreadUtils.randomThreadWait
import dev.gathi.utils.ThreadUtils.randomWait
import kotlin.random.Random

interface WeatherService {
    fun getWeather(city: String): String
    suspend fun getWeatherAsync(city: String): WeatherResult
}

sealed class WeatherResult {
    data class Success(val weather: String): WeatherResult()
    data class Failure(val error: Throwable): WeatherResult()
}


object WeatherHandler {
    fun handle(city: String, serviceId: Int): String {

        when (serviceId) {
            1 -> if (city == "Palm Beach" || city == "Fargo") {
                throw RuntimeException("Weather Service One is down")
            }
            2 -> if (city == "Palm Beach" || city == "Moscow") {
                throw RuntimeException("Weather Service Two is down")
            }
            3 -> if (city == "Palm Beach" || city == "Moscow") {
                throw RuntimeException("Weather Service Three is down")
            }
        }


        return "Service: ($serviceId) - Weather in $city is ${Random.nextInt(-1, 80)} degrees."
    }
}

class WeatherServiceOne: WeatherService {

    override fun getWeather(city: String): String {
        Thread.currentThread().randomThreadWait("Weather Service One")
        return WeatherHandler.handle(city, 1)
    }


    override suspend fun getWeatherAsync(city: String): WeatherResult {

        return try {
            randomWait("Weather Service One")
            WeatherResult.Success(WeatherHandler.handle(city, 1))
        } catch (e: Exception) {
            WeatherResult.Failure(e)
        }
    }
}

class WeatherServiceTwo: WeatherService {

    override fun getWeather(city: String): String {
        Thread.currentThread().randomThreadWait("Weather Service Two")
        return WeatherHandler.handle(city, 2)
    }

    override suspend fun getWeatherAsync(city: String): WeatherResult {

        return try {
            randomWait("Weather Service Two", 100)
            WeatherResult.Success(WeatherHandler.handle(city, 2))
        }
        catch (e: Exception) {
            WeatherResult.Failure(e)
        }
    }
}

class WeatherServiceThree: WeatherService {

    override fun getWeather(city: String): String {
        Thread.currentThread().randomThreadWait("Weather Service Three")
        return WeatherHandler.handle(city, 3)
    }

    override suspend fun getWeatherAsync(city: String): WeatherResult {

        return try {
            randomWait("Weather Service Three", 200)
            WeatherResult.Success(WeatherHandler.handle(city, 3))
        }
        catch (e: Exception) {
            WeatherResult.Failure(e)
        }
    }
}

