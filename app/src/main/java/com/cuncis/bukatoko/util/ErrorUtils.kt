package com.cuncis.bukatoko.util

import retrofit2.HttpException
import java.net.UnknownHostException
import javax.net.ssl.HttpsURLConnection

object ErrorUtils {

    fun getErrorThrowableMessage(error: Throwable): String = when (error) {
        is HttpException -> {
            when (error.code()) {
                HttpsURLConnection.HTTP_UNAUTHORIZED -> "Tidak dapat mengakses data"
                HttpsURLConnection.HTTP_NOT_FOUND -> "Data tidak ditemukan"
                HttpsURLConnection.HTTP_INTERNAL_ERROR -> "Terjadi gangguan pada server"
                HttpsURLConnection.HTTP_BAD_REQUEST -> "Data tidak sesuai"
                HttpsURLConnection.HTTP_FORBIDDEN -> "Sesi telah berakhir"
                else -> "Oops, Terjadi gangguan, coba lagi beberapa saat"
            }
        }
        is UnknownHostException -> "Tidak ada koneksi internet"
        else -> "Terjadi kesalahan"
    }

    fun getErrorThrowableCode(error: Throwable) = when (error) {
        is HttpException -> {
            when (error.code()) {
                HttpsURLConnection.HTTP_UNAUTHORIZED -> 401
                HttpsURLConnection.HTTP_NOT_FOUND -> 404
                HttpsURLConnection.HTTP_INTERNAL_ERROR -> 500
                HttpsURLConnection.HTTP_BAD_REQUEST -> 400
                HttpsURLConnection.HTTP_FORBIDDEN -> 403
                HttpsURLConnection.HTTP_CONFLICT -> 409
                else -> error.code()
            }
        } else -> 500
    }
}