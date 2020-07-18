package com.cuncis.bukatoko.data.model

object TransactionData {
    data class Data(
        var user_id: Int = 0,
        var destination: String = "",
        var ongkir: Int = 0,
        var grandtotal: Int = 0,
        var detail: List<Details> = arrayListOf()
    ) {
        data class Details(
            var product_id: Int = 0,
            var qty: Int = 0,
            var price: Int = 0,
            var total: Int = 0
        )
    }
}