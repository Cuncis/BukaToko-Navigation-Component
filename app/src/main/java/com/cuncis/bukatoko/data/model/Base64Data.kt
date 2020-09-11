package com.cuncis.bukatoko.data.model

object Base64Data {
	data class Response(
		val data: List<Data>,
		val status: Boolean
	) {
		data class Data(
			val file: String,
			val catatan: String,
			val judul: String
		)
	}
}
