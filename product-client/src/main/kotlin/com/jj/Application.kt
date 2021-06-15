package com.jj

import io.micronaut.runtime.Micronaut.*
suspend fun main(args: Array<String>) {

	val productService = ProductService()
	//productService.saveProduct()
	productService.saveProductStream()

	build()
	    .args(*args)
		.packages("com.jj")
		.start()
}

