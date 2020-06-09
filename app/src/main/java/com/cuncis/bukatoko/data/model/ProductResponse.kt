package com.cuncis.bukatoko.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductResponse(

	@SerializedName("data")
	val data: List<Product>,

	@SerializedName("meta")
	val meta: Meta,

	@SerializedName("links")
	val links: Links
) : Parcelable

@Parcelize
data class Meta(

	@SerializedName("path")
	val path: String,

	@SerializedName("per_page")
	val perPage: Int,

	@SerializedName("total")
	val total: Int,

	@SerializedName("last_page")
	val lastPage: Int,

	@SerializedName("from")
	val from: Int,

	@SerializedName("to")
	val to: Int,

	@SerializedName("current_page")
	val currentPage: Int
) : Parcelable

@Parcelize
data class Links(

	@SerializedName("next")
	val next: String,

	@SerializedName("last")
	val last: String,

	@SerializedName("prev")
	val prev: String,

	@SerializedName("first")
	val first: String
) : Parcelable


@Parcelize
data class Product(

	@SerializedName("image")
	val image: String,

	@SerializedName("product")
	val product: String,

	@SerializedName("price")
	val price: Int,

	@SerializedName("description")
	val description: String,

	@SerializedName("id")
	val id: Int,

	@SerializedName("stock")
	val stock: Int
) : Parcelable
