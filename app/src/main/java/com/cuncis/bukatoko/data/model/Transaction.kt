package com.cuncis.bukatoko.data.model

import com.google.gson.annotations.SerializedName

object Transaction {
    data class Response(
        val data: MutableList<Data>
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
    }
}