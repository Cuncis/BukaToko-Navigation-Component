package com.cuncis.bukatoko.data.model

import com.google.gson.annotations.SerializedName

object City {
    data class Response(
        @SerializedName("rajaongkir")
        val result: Results
    ) {
        data class Results(
            @SerializedName("results")
            val results: List<Data>
        ) {
            data class Data(
                @SerializedName("city_name")
                val cityName: String,

                @SerializedName("province")
                val province: String,

                @SerializedName("province_id")
                val provinceId: String,

                @SerializedName("type")
                val type: String,

                @SerializedName("postal_code")
                val postalCode: String,

                @SerializedName("city_id")
                val cityId: String
            )
        }
    }
}