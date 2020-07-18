package com.cuncis.bukatoko.data.repository

import com.cuncis.bukatoko.data.api.TheShippingPaymentApi
import com.cuncis.bukatoko.data.model.City
import com.cuncis.bukatoko.data.model.Cost

class ApiRepoRajaOngkir(private val apiService: TheShippingPaymentApi) {

    suspend fun getCities(): City.Response {
        return apiService.getCities()
    }

    suspend fun postCost(origin: String, destination: String, weight: String, courier: String): Cost.Response {
        return apiService.postCost(origin, destination, weight, courier)
    }

}