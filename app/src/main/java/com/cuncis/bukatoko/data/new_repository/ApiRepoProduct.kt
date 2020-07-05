package com.cuncis.bukatoko.data.new_repository

import com.cuncis.bukatoko.data.api.TheShoppingApi
import com.cuncis.bukatoko.data.new_model.Detail
import com.cuncis.bukatoko.data.new_model.Product
import com.cuncis.bukatoko.data.new_model.User

class ApiRepoProduct(private val apiService: TheShoppingApi) {

    suspend fun getAllProduct(): Product.Response {
        return apiService.getAllProducts()
    }

    suspend fun getDetailProduct(productId: String): Detail.Response {
        return apiService.getDetailProduct(productId)
    }

    suspend fun postLogin(email: String, password: String): User.Response {
        return apiService.login(email, password)
    }

}