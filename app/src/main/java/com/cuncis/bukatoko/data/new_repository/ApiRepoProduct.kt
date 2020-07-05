package com.cuncis.bukatoko.data.new_repository

import com.cuncis.bukatoko.data.api.TheShoppingApi
import com.cuncis.bukatoko.data.new_model.Product

class ApiRepoProduct(private val apiService: TheShoppingApi) {

    suspend fun getAllProduct(): Product.Response {
        return apiService.getAllProducts()
    }

}