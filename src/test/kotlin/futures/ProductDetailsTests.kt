package futures

import dev.gathi.futures.ProductDetails
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ProductDetailsTests(
) {

    private val productDetails: ProductDetails = ProductDetails()

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
       }
    }

}