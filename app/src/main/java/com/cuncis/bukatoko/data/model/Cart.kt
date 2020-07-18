package com.cuncis.bukatoko.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "cart_table")
@Parcelize
data class Cart(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "product_id")
    var productId: Int = 0,

    @ColumnInfo(name = "product_name")
    var productName: String = "",

    @ColumnInfo(name = "image_url")
    var imageUrl: String = "",

    @ColumnInfo(name = "price")
    var price: Double = 0.0,

    @ColumnInfo(name = "qty")
    var qty: Int = 1,

    @ColumnInfo(name = "total")
    var total: Double = 0.0,

    @ColumnInfo(name = "current_date")
    var currentDate: String = ""
): Parcelable