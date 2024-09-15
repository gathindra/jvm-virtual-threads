package virtual

import dev.gathi.virtual.ProductDetailsVirtual
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ProductDetailsVirtualTest {
    private val productDetails: ProductDetailsVirtual = ProductDetailsVirtual()

    @Test
    fun `should return product details`() {
        val product = productDetails.run(123)
        assertSoftly(product) {
            sku shouldBe 123
            upc shouldBe "123"
            name shouldBe "Product 1"
            price shouldBe 10.0
        }
    }

    @Test
    fun `should throw exception when product not found`() {
        shouldThrow<RuntimeException> {
            productDetails.run(999)
        }
    }
}