package com.cuncis.bukatoko.data.model

object Upload {
    data class Response(
        val status: Status
    ) {
        data class Status(
            val code: String,
            val description: String
        )
    }
}