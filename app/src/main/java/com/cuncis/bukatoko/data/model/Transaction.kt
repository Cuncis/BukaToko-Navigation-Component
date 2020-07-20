package com.cuncis.bukatoko.data.model

object Transaction {
    data class Response(
        val data: MutableList<Data>,
        val status: Status
    ) {
        data class Data(
            val resi_code: String,
            val transaction_code: String,
            val date_transaction: String,
            val ongkir: String,
            val destination: String,
            val grandtotal: String,
            val status_transaction: String,
            val kurir: String,
            val user: String
        )
        data class Status(
            val code: Int,
            val description: String
        )
    }
}