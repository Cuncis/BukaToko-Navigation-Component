package com.cuncis.bukatoko.data.api

import com.cuncis.bukatoko.data.model.ProductDetailResponse
import com.cuncis.bukatoko.data.model.ProductResponse
import com.cuncis.bukatoko.data.model.Users
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface TheShoppingApi {

//    @GET("products")
//    fun getAllProducts(): Call<ProductResponse>

    @GET("products")
    suspend fun getAllProducts(): Response<ProductResponse>

    @GET("product/{id}")
        fun getProductById(@Path("id") productId: String
    ): Call<ProductDetailResponse>

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