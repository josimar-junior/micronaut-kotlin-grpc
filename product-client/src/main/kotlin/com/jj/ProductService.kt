package com.jj

import io.grpc.Channel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class ProductService {

    suspend fun saveProduct() {
        val productServerStub = createStub()
        val saveProductRequest = SaveProductRequest.newBuilder()
            .setName("Notebook")
            .setQuantity(5)
            .setValue(2500.0)
            .build()
        val saveProductResponse = productServerStub.saveProduct(saveProductRequest)
        println("Product saved with id = " + saveProductResponse.id)
    }

    private fun createStub() : ProductServerServiceGrpcKt.ProductServerServiceCoroutineStub {
        var channel: Channel = ManagedChannelBuilder.forAddress("localhost", 50051)
            .usePlaintext()
            .build()
        return ProductServerServiceGrpcKt.ProductServerServiceCoroutineStub(channel)
    }

    suspend fun saveProductStream() {
        val productServerStub = createStub()
        val requests = generateRequests()

        productServerStub.saveProductStream(requests).collect { response ->
            println("Response: " + response.id)
        }
    }

    private fun generateRequests(): Flow<SaveProductRequest> = flow {
        val productRequest1 = SaveProductRequest.newBuilder()
            .setName("Notebook")
            .setQuantity(5)
            .setValue(2500.0)
            .build()

        val productRequest2 = SaveProductRequest.newBuilder()
            .setName("iPhone")
            .setQuantity(3)
            .setValue(5000.0)
            .build()

        val productRequest3 = SaveProductRequest.newBuilder()
            .setName("Mouse")
            .setQuantity(10)
            .setValue(100.0)
            .build()

        val requests = listOf(productRequest1, productRequest2, productRequest3)

        for(request in requests) {
            println("Request: " + request.name)
            emit(request)
            delay(5000)
        }
    }
}