package coroutines

import dev.gathi.coroutines.CoroutineWeatherDetails
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.string.shouldContain

import org.junit.jupiter.api.Test

class CoroutineWeatherDetailsTest {
    private val weatherDetails = CoroutineWeatherDetails()

    @Test
    fun `should return weather details`() {
        val weather = weatherDetails.run("Nairobi")
        weather shouldContain "Weather in Nairobi is"
    }

    @Test
    fun `should throw exception when city not found`() {
        shouldThrow<RuntimeException> {
            weatherDetails.run("Palm Beach")
        }
    }

    @Test
    fun `should not throw exception when city is found`() {
        shouldNotThrow<RuntimeException> {
            val weather = weatherDetails.run("Moscow")
            weather shouldContain "Service: (1) - Weather in Moscow is "
        }
    }

}