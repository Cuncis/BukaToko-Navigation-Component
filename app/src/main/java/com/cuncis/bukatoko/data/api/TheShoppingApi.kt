package com.cuncis.bukatoko.data.api

import com.cuncis.bukatoko.data.model.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface TheShoppingApi {

    @GET("products")
    suspend fun getAllProducts(): Product.Response

    @GET("product/{id}")
    suspend fun getDetailProduct(@Path("id") productId: String): Detail.Response

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): User.Response

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun postRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): User.Response

    @FormUrlEncoded
    @POST("auth/update/{id}")
    suspend fun postUpdate(
        @Path("id") userId: Int,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): User.Response

    @POST("transaction")
    suspend fun postTransaction(
        @Body transactionData: TransactionData.Data
    ): TransactionData.Data

    @GET("transaction-user/{id}/unpaid")
    suspend fun getTransactionUnpaid(
        @Path("id") userId: String
    ): Transaction.Response

    @GET("transaction-user/{id}/paid")
    suspend fun getTransactionPaid(
        @Path("id") userId: String
    ): Transaction.Response

    @Multipart
    @POST("upload/{code}")
    suspend fun postUploadImage(
        @Path("code") code: String,
        @Part imageFile: MultipartBody.Part
    ): Upload.Response

}