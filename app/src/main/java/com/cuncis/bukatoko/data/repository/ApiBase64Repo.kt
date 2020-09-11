package com.cuncis.bukatoko.data.repository

import com.cuncis.bukatoko.data.api.TheBase64Api
import com.cuncis.bukatoko.data.model.Base64Data

class ApiBase64Repo(private val apiService: TheBase64Api) {

    suspend fun getBase64Data(): Base64Data.Response {
        return apiService.getBase64Data()
    }
}