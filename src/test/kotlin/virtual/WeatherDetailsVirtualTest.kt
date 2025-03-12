package virtual

import dev.gathi.virtual.WeatherDetailsVirtual
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test


class WeatherDetailsVirtualTest {

    private val weatherDetails: WeatherDetailsVirtual = WeatherDetailsVirtual()

    @Test
    fun `should return weather details for a given city`() {
        val weather = weatherDetails.run("Nairobi")
        weather shouldContain "Weather in Nairobi is"
    }

    @Test
    fun `should throw an exception when a city not found by any of the services`() {
        shouldThrow<RuntimeException> {
            weatherDetails.run("Palm Beach")
        }
    }

    @Test
    fun `should not throw an exception when a city is found by at least one of the services`() {
        shouldNotThrow<RuntimeException> {
            val weather = weatherDetails.run("Fargo")
            weather shouldContain "Weather in Fargo is"
        }
    }

}