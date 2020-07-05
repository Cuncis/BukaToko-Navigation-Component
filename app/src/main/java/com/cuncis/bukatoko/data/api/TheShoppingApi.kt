package com.cuncis.bukatoko.data.api

import com.cuncis.bukatoko.data.new_model.Detail
import com.cuncis.bukatoko.data.new_model.Product
import com.cuncis.bukatoko.data.new_model.User
import retrofit2.Call
import retrofit2.http.*

interface TheShoppingApi {

    @GET("products")
    suspend fun getAllProducts(): Product.Response

    @GET("product/{id}")
    suspend fun getDetailProduct(@Path("id") productId: String): Detail.Response

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(@Field("email") email: String,
                      @Field("password") password: String
    ): User.Response

    @FormUrlEncoded
    @POST("auth/register")
    fun register(@Field("name") name: String,
                 @Field("email") email: String,
                 @Field("password") password: String
    ): Call<User.Response>

    @FormUrlEncoded
    @POST("auth/update/{id}")
    fun updateUser(@Path("id") userId: Int,
                   @Field("name") name: String,
                   @Field("email") email: String,
                   @Field("password") password: String
    ): Call<User.Response>

}