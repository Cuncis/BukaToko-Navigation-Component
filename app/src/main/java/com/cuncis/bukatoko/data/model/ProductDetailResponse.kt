package com.cuncis.bukatoko.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductDetailResponse(

	@SerializedName("data")
	val data: Data
) : Parcelable

@Parcelize
data class Data(

	@SerializedName("image")
	val image: String,

	@SerializedName("product")
	val product: String,

	@SerializedName("images")
	val images: ArrayList<Images>,

	@SerializedName("price")
	val price: Int,

	@SerializedName("description")
	val description: String,

	@SerializedName("id")
	val id: Int,

	@SerializedName("stock")
	val stock: Int
) : Parcelable

@Parcelize
data class Images(

	@SerializedName("image")
	val image: String,

	@SerializedName("id")
	val id: Int
) : Parcelable
