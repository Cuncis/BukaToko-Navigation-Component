package com.cuncis.bukatoko.data.api

import com.cuncis.bukatoko.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


fun provideHttpLoggingInterceptor() = run {
    HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

fun provideOkHttpClient() = run {
    val okHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(provideHttpLoggingInterceptor())
    okHttpClient.build()
}

fun provideClientProduct(okHttpClient: OkHttpClient): TheShoppingApi {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TheShoppingApi::class.java)
}

fun provideClientRajaOngkir(okHttpClient: OkHttpClient): TheShippingPaymentApi {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.RAJA_ONGKIR_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TheShippingPaymentApi::class.java)
}