package com.cuncis.bukatoko.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
data class Cart(
    @ColumnInfo(name = "product_id")
    var productId: Int = 0,

    @ColumnInfo(name = "product_name")
    var productName: String = "",

    @ColumnInfo(name = "image_url")
    var imageUrl: String = "",

    @ColumnInfo(name = "price")
    var price: Double = 0.0,

    @ColumnInfo(name = "qty")
    var qty: Int = 0,

    @ColumnInfo(name = "total")
    var total: Double = 0.0,

    @ColumnInfo(name = "current_date")
    var currentDate: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}