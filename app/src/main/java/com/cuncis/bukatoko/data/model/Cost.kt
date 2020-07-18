package com.cuncis.bukatoko.data.model



import com.google.gson.annotations.SerializedName

object Cost {
    data class Response(
        val rajaongkir: Rajaongkir
    ) {
        data class Rajaongkir(
            val query: Query,

            @SerializedName("destination_details")
            val destinationDetails: DestinationDetails,

            @SerializedName("origin_details")
            val originDetails: OriginDetails,
            val results: List<Results>,
            val status: Status
        ) {
            data class Query(
                val courier: String,
                val origin: String,
                val destination: String,
                val weight: Int,
                val key: String
            )
            data class DestinationDetails(
                @SerializedName("city_name")
                val cityName: String,

                val province: String,

                @SerializedName("province_id")
                val provinceId: String,

                val type: String,

                @SerializedName("postal_code")
                val postalCode: String,

                @SerializedName("city_id")
                val cityId: String
            )
            data class OriginDetails(
                @SerializedName("city_name")
                val cityName: String,

                val province: String,

                @SerializedName("province_id")
                val provinceId: String,

                val type: String,

                @SerializedName("postal_code")
                val postalCode: String,

                @SerializedName("city_id")
                val cityId: String
            )
            data class Status(
                val code: Int,
                val description: String
            )
            data class Results(
                val costs: List<Costs>,
                val code: String,
                val name: String
            ) {
                data class Costs(
                    val cost: List<CostItem>,
                    val service: String,
                    val description: String
                ) {
                    data class CostItem(
                        val note: String,
                        val etd: String,
                        val value: Int
                    )
                }
            }
        }
    }
}