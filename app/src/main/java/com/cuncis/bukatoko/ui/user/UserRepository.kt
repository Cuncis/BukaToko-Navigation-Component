package com.cuncis.bukatoko.ui.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cuncis.bukatoko.data.api.ApiClient
import com.cuncis.bukatoko.data.model.Users
import com.cuncis.bukatoko.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    val users = MutableLiveData<Users>()
    val loading = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()

    fun login(email: String, password: String): MutableLiveData<Users> {
        loading.value = true
        ApiClient.theShoppingApi.login(email, password)
            .enqueue(object : Callback<Users> {
                override fun onResponse(call: Call<Users>, response: Response<Users>) {
                    loading.value = false
                    if (response.isSuccessful) {
                        users.postValue(response.body())
                    } else {
                        message.postValue(response.message())
                    }
                }
                override fun onFailure(call: Call<Users>, t: Throwable) {
                    loading.value = false
                    message.postValue(t.message)
                }
            })

        return users
    }

    fun register(name: String, email: String, password: String): MutableLiveData<Users> {
        loading.value = true
        ApiClient.theShoppingApi.register(name, email, password)
            .enqueue(object : Callback<Users> {
                override fun onResponse(call: Call<Users>, response: Response<Users>) {
                    loading.value = false
                    if (response.isSuccessful) {
                        users.postValue(response.body())
                    } else {
                        message.postValue(response.message())
                    }
                }
                override fun onFailure(call: Call<Users>, t: Throwable) {
                    loading.value = false
                    message.postValue(t.message)
                }
            })

        return users
    }

    fun update(userId: Int, name: String, email: String, password: String): MutableLiveData<Users> {
        loading.value = true
        ApiClient.theShoppingApi.updateUser(userId, name, email, password)
            .enqueue(object : Callback<Users> {
                override fun onResponse(call: Call<Users>, response: Response<Users>) {
                    loading.value = false
                    if (response.isSuccessful) {
                        users.postValue(response.body())
                    } else {
                        message.postValue(response.message())
                    }
                }
                override fun onFailure(call: Call<Users>, t: Throwable) {
                    loading.value = false
                    message.postValue(t.message)
                }
            })

        return users
    }

}