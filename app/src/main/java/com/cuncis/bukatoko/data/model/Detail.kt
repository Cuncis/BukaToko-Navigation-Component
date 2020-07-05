package com.cuncis.bukatoko.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

object Detail {
    @Parcelize
    data class Data(
        val image: String,
        val product: String,
        val images: ArrayList<Images>,
        val price: Int,
        val description: String,
        val id: Int,
        val stock: Int
    ) : Parcelable

    @Parcelize
    data class Images(
        val image: String,
        val id: Int
    ) : Parcelable

    data class Response(
        val data: Data
    )
}