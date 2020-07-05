package com.cuncis.bukatoko.data.api

import com.cuncis.bukatoko.data.model.Users
import com.cuncis.bukatoko.data.new_model.Detail
import com.cuncis.bukatoko.data.new_model.Product
import retrofit2.Call
import retrofit2.http.*

interface TheShoppingApi {

    @GET("products")
    suspend fun getAllProducts(): Product.Response

    @GET("product/{id}")
    suspend fun getDetailProduct(@Path("id") productId: String): Detail.Response

    @FormUrlEncoded
    @POST("auth/login")
    fun login(@Field("email") email: String,
              @Field("password") password: String
    ): Call<Users>

    @FormUrlEncoded
    @POST("auth/register")
    fun register(@Field("name") name: String,
                 @Field("email") email: String,
                 @Field("password") password: String
    ): Call<Users>

    @FormUrlEncoded
    @POST("auth/update/{id}")
    fun updateUser(@Path("id") userId: Int,
                   @Field("name") name: String,
                   @Field("email") email: String,
                   @Field("password") password: String
    ): Call<Users>

}