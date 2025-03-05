package dev.gathi.coroutines

import dev.gathi.services.WeatherResult
import dev.gathi.services.WeatherResult.Success
import dev.gathi.services.WeatherServiceOne
import dev.gathi.services.WeatherServiceThree
import dev.gathi.services.WeatherServiceTwo
import dev.gathi.utils.KotlinCoroutineDispatcherProvider.LOOM
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.supervisorScope

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

            return@runBlocking (awaitAny(weatherOne, weatherTwo, weatherThree) as Success).weather

        } catch (e: Exception) {
            println("Exception occurred: ${e.message}")
            throw RuntimeException("Weather Details not found")
        }
    }

    /**
     * Awaits the result of any of the provided deferred results.
     * It returns the first successful result or throws an exception if all results are failures.
     * runs in a supervisor scope to allow for concurrent execution without cancelling the entire scope.
     * @param resultsDeferred Vararg of deferred results to await.
     * @return The first successful result.
     * @throws RuntimeException if all deferred results are failures.
     */
    private suspend fun <T> awaitAny(vararg resultsDeferred: Deferred<T>): T {
        return supervisorScope {
            val results = resultsDeferred.map { it.await() }
            results.firstOrNull { it is Success } ?: throw RuntimeException("All services failed")

        }
    }

}