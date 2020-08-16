package com.cuncis.bukatoko.data.model

import com.google.gson.annotations.SerializedName

object TransactionDetail{
	data class Response(
		@SerializedName("data")
		val data: Data
	) {
		data class Data(
			@SerializedName("resi_code")
			val resiCode: String,

			@SerializedName("transaction_code")
			val transactionCode: String,

			@SerializedName("date_transaction")
			val dateTransaction: String,

			@SerializedName("ongkir")
			val ongkir: String,

			@SerializedName("destination")
			val destination: String,

			@SerializedName("grandtotal")
			val grandtotal: String,

			@SerializedName("detail_transaction")
			val detailTransaction: List<Detail>,

			@SerializedName("status_transaction")
			val statusTransaction: String,

			@SerializedName("kurir")
			val kurir: Any,

			@SerializedName("user")
			val user: String
		) {
			data class Detail(
				@SerializedName("product")
				val product: String,

				@SerializedName("total")
				val total: String,

				@SerializedName("price")
				val price: String,

				@SerializedName("product_id")
				val productId: Int,

				@SerializedName("qty")
				val qty: Int
			)
		}
	}
}



