package dev.gathi.coroutines

import dev.gathi.services.WeatherResult
import dev.gathi.services.WeatherResult.Success
import dev.gathi.services.WeatherServiceOne
import dev.gathi.services.WeatherServiceThree
import dev.gathi.services.WeatherServiceTwo
import dev.gathi.utils.KotlinCoroutineDispatcherProvider.LOOM
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

class CoroutineWeatherDetails(
    private val weatherServiceOne: WeatherServiceOne = WeatherServiceOne(),
    private val weatherServiceTwo: WeatherServiceTwo = WeatherServiceTwo(),
    private val weatherServiceThree: WeatherServiceThree = WeatherServiceThree()
) {

    fun run(city: String): String = runBlocking(Dispatchers.LOOM) {
        try {
            val weatherOne: Deferred<WeatherResult> = async { weatherServiceOne.getWeatherAsync(city) }
            val weatherTwo: Deferred<WeatherResult> = async { weatherServiceTwo.getWeatherAsync(city) }
            val weatherThree: Deferred<WeatherResult> = async { weatherServiceThree.getWeatherAsync(city) }

            return@runBlocking awaitAny(weatherOne, weatherTwo, weatherThree)

        } catch (e: Exception) {
            println("Exception occurred: ${e.message}")
            throw RuntimeException("Weather Details not found")
        }
    }

    /**
     * Awaits for the first successful result from the provided deferred objects.
     *
     * This function uses the `select` construct from the Kotlin coroutines library to wait for the first
     * successful result among the provided deferred objects. The `select` construct allows for waiting
     * on multiple suspending functions simultaneously and resumes with the first one that completes.
     *
     * @param resultsDeferred Vararg parameter of deferred objects to await.
     * @return The weather details from the first successful result.
     * @throws RuntimeException if all services fail to provide a successful result.
     */
    private suspend fun <T> awaitAny(vararg resultsDeferred: Deferred<T>): String {
        return select<Success?> {
            // Wait for the first successful result
            resultsDeferred.forEach { deferred ->
                deferred.onAwait {
                    when (it) {
                        is Success -> {
                            println("${it.weather} - thread: ${Thread.currentThread()}")
                            it
                        }
                        else -> null
                    }
                }
            }
        }.let { result: Success? ->
            result ?: throw RuntimeException("All services failed")
        }.weather

    }

}