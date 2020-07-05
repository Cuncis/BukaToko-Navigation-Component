package com.cuncis.bukatoko.data.model

object User {
    data class Data(
        val isAdmin: Int,
        val tokenFirebase: String,
        val apiToken: String,
        val name: String,
        val id: Int,
        val email: String,
        val username: String
    )

    data class Response(
        val data: Data
    )
}