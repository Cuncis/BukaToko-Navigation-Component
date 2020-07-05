package com.cuncis.bukatoko.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

object Product {
    @Parcelize
    data class Data(
        val image: String,
        val product: String,
        val price: Int,
        val description: String,
        val id: Int,
        val stock: Int
    ): Parcelable

    data class Response(
        val data: List<Data>
    )
}