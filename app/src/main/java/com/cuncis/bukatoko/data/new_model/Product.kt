package com.cuncis.bukatoko.data.new_model

import android.os.Parcelable
import com.cuncis.bukatoko.data.model.Links
import com.cuncis.bukatoko.data.model.Meta
import com.cuncis.bukatoko.data.model.Product
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
        val data: List<Product>,
        val meta: Meta,
        val links: Links
    )
}