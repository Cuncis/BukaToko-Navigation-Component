package com.cuncis.bukatoko.data.api

import com.cuncis.bukatoko.data.model.Base64Data
import retrofit2.http.GET

interface TheBase64Api {
    @GET("RestApi/SlideAndroid")
    suspend fun getBase64Data(): Base64Data.Response
}