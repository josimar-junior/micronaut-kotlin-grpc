package com.jj

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Singleton
class ProductServerService : ProductServerServiceGrpcKt.ProductServerServiceCoroutineImplBase() {

    override suspend fun saveProduct(productRequest: SaveProductRequest): ProductResponse {
        return ProductResponse.newBuilder()
            .setId(1)
            .setName(productRequest.name)
            .setQuantity(productRequest.quantity)
            .setValue(productRequest.value)
            .build()
    }

    override fun saveProductStream(requests: Flow<SaveProductRequest>): Flow<ProductResponse> = flow {
        var id = 1
        requests.collect {
            println("Saving product...")
            emit(
                ProductResponse.newBuilder()
                    .setId(id++)
                    .setName(it.name)
                    .setQuantity(it.quantity)
                    .setValue(it.value)
                    .build()
            )
            println("Concluded!")
        }
    }
}