package com.cuncis.bukatoko.data.api

import com.cuncis.bukatoko.data.model.City
import com.cuncis.bukatoko.data.model.Cost
import retrofit2.http.*

interface TheShippingPaymentApi {

    @GET("city")
    suspend fun getCities(@Query("key") key: String): City.Response

    @FormUrlEncoded
    @POST("cost")
    suspend fun getCost(@Field("key") key: String,
                        @Field("origin") origin: String,
                        @Field("destination") destination: String,
                        @Field("weight") weight: String,
                        @Field("courier") courier: String
    ): Cost.Response

}