package coroutines

import dev.gathi.coroutines.CoroutineProductDetails
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldNotBeOneOf
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class CoroutineProductDetailsTest {

    private val productDetails: CoroutineProductDetails = CoroutineProductDetails()

    @Test
    fun `should return a correct product details`() {
        val product = productDetails.run(123)
        assertSoftly(product) {
            sku shouldBe 123
            upc shouldBe "123"
            name shouldBe "Product 1"
            price shouldBe 10.0
        }
    }

    @Test
    fun `should throw an exception when a product is not found`() {
        shouldThrow<RuntimeException> {
            productDetails.run(999)
        }.message shouldNotBeOneOf  listOf("Product Details not found", "Product Price not found")
    }
}