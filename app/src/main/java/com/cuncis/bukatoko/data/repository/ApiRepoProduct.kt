package com.cuncis.bukatoko.data.repository

import com.cuncis.bukatoko.data.api.TheShoppingApi
import com.cuncis.bukatoko.data.model.*
import okhttp3.MultipartBody

class ApiRepoProduct(private val apiService: TheShoppingApi) {

    suspend fun getAllProduct(): Product.Response {
        return apiService.getAllProducts()
    }

    suspend fun getDetailProduct(productId: String): Detail.Response {
        return apiService.getDetailProduct(productId)
    }

    suspend fun postLogin(email: String, password: String): User.Response {
        return apiService.postLogin(email, password)
    }

    suspend fun postRegister(name: String, email: String, password: String): User.Response {
        return apiService.postRegister(name, email, password)
    }

    suspend fun postUpdate(userId: Int, name: String, email: String, password: String): User.Response {
        return apiService.postUpdate(userId, name, email, password)
    }

    suspend fun postTransaction(transactionData: TransactionData.Data): TransactionData.Data {
        return apiService.postTransaction(transactionData)
    }

    suspend fun getTransactionUnpaid(userId: String): Transaction.Response {
        return apiService.getTransactionUnpaid(userId)
    }

    suspend fun getTransactionPaid(userId: String): Transaction.Response {
        return apiService.getTransactionPaid(userId)
    }

    suspend fun postUploadImage(code: String, imageFile: MultipartBody.Part): Upload.Response {
        return apiService.postUploadImage(code, imageFile)
    }
}