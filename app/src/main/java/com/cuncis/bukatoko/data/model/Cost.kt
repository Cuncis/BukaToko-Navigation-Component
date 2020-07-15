package com.cuncis.bukatoko.data.model


import com.google.gson.annotations.SerializedName

object Cost {
    data class Response(
        val rajaongkir: Rajaongkir
    ) {
        data class Rajaongkir(
            val destinationDetails: DestinationDetails,
            val originDetails: OriginDetails,
            val results: List<Results>,
            val status: Status
        ) {
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

            data class DestinationDetails(
                val cityName: String,
                val province: String,
                val provinceId: String,
                val type: String,
                val postalCode: String,
                val cityId: String
            )

            data class OriginDetails(
                val cityName: String,
                val province: String,
                val provinceId: String,
                val type: String,
                val postalCode: String,
                val cityId: String
            )

            data class Status(
                val code: Int,
                val description: String
            )
        }
    }

}