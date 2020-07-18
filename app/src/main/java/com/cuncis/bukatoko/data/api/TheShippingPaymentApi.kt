package com.cuncis.bukatoko.data.api

import com.cuncis.bukatoko.BuildConfig
import com.cuncis.bukatoko.data.model.City
import com.cuncis.bukatoko.data.model.Cost
import retrofit2.http.*

interface TheShippingPaymentApi {

    @GET("city")
    suspend fun getCities(@Query("key") key: String = BuildConfig.KEY_RAJAONGKIR)
            : City.Response

    @FormUrlEncoded
    @POST("cost")
    suspend fun postCost(@Field("origin") origin: String,
                        @Field("destination") destination: String,
                        @Field("weight") weight: String,
                        @Field("courier") courier: String,
                        @Field("key") key: String = BuildConfig.KEY_RAJAONGKIR
    ): Cost.Response

}