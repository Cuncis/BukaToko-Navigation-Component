package com.cuncis.bukatoko.data.model

object TransactionData {
    data class Data(
        val user_id: Int,
        val destination: String,
        val ongkir: Int,
        val grandtotal: Int,
        val detail: List<Details>
    ) {
        data class Details(
            val product_id: Int,
            val qty: Int,
            val price: Int,
            val total: Int
        )
    }
}