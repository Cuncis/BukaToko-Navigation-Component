package com.cuncis.bukatoko.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

object Product {
    @Parcelize
    data class Data(
        val id: Int,
        val image: String,
        val product: String,
        val price: Int,
        val description: String,
        val stock: Int
    ): Parcelable

    data class Response(
        val data: List<Data>
    )
}