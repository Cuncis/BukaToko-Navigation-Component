package com.cuncis.bukatoko.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users(

	@SerializedName("data")
	val data: UserData
) : Parcelable

@Parcelize
data class UserData(

	@SerializedName("is_admin")
	val isAdmin: Int,

	@SerializedName("token_firebase")
	val tokenFirebase: String,

	@SerializedName("api_token")
	val apiToken: String,

	@SerializedName("name")
	val name: String,

	@SerializedName("id")
	val id: Int,

	@SerializedName("email")
	val email: String,

	@SerializedName("username")
	val username: String
) : Parcelable
