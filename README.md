

# Structured Concurrency with Java Futures, Virtual Threads, and Kotlin Coroutines

This project demonstrates the implementation of **structured concurrency** using:
- **Java Futures**
- **Virtual Threads** (introduced in Project Loom)
- **Kotlin Coroutines**

This project demonstrate structured concurrency with Java `Completable Futures`, `Virtual Threads` and `Kotlin Coroutines` 🧵Hope you find it useful! 😎Here's everything you need to know to get started.

Implementations are demonstrated as `Unit Tests` in the project. You can run them to see how the threads are managed and how the structured concurrency is implemented.

## Project Structure

### Main Code
- **Java Futures**: Implemented in `ProductDetails.java` using `CompletableFuture`.
- **Virtual Threads**: Demonstrated in `ProductDetailsVirtual.kt` using `StructuredTaskScope`.
- **Kotlin Coroutines**: Implemented in `CoroutineProductDetails.kt` using `Dispatchers.LOOM`.

### Services
- **ProductService**: Fetches product details by SKU.
- **PricingService**: Fetches product pricing.
- Coroutine equivalents for both services are provided.

### Utilities
- **ThreadUtils**: Simulates random delays for realistic thread behavior.
- **KotlinCoroutineDispatcherProvider**: Custom Loom-based dispatcher.

### Test Files
- `CoroutineProductDetailsTest.kt`: Tests for coroutines.
- `ProductDetailsTests.kt`: Tests for Java futures.
- `ProductDetailsVirtualTest.kt`: Tests for virtual threads.

## How to Run the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/gathindra/jvm-virtual-threads.git
   cd jvm-virtual-threads
   ```
   
2. Run the tests:
   ```bash
    ./gradlew test
    ```
