package com.cuncis.bukatoko.ui.user

import androidx.lifecycle.MutableLiveData
import com.cuncis.bukatoko.data.api.ApiClient
import com.cuncis.bukatoko.data.new_model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    val users = MutableLiveData<User.Response>()
    val loading = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()

    fun register(name: String, email: String, password: String): MutableLiveData<User.Response> {
        loading.value = true
        ApiClient.theShoppingApi.register(name, email, password)
            .enqueue(object : Callback<User.Response> {
                override fun onResponse(call: Call<User.Response>, response: Response<User.Response>) {
                    loading.value = false
                    if (response.isSuccessful) {
                        users.postValue(response.body())
                    } else {
                        message.postValue(response.message())
                    }
                }
                override fun onFailure(call: Call<User.Response>, t: Throwable) {
                    loading.value = false
                    message.postValue(t.message)
                }
            })

        return users
    }

    fun update(userId: Int, name: String, email: String, password: String): MutableLiveData<User.Response> {
        loading.value = true
        ApiClient.theShoppingApi.updateUser(userId, name, email, password)
            .enqueue(object : Callback<User.Response> {
                override fun onResponse(call: Call<User.Response>, response: Response<User.Response>) {
                    loading.value = false
                    if (response.isSuccessful) {
                        users.postValue(response.body())
                    } else {
                        message.postValue(response.message())
                    }
                }
                override fun onFailure(call: Call<User.Response>, t: Throwable) {
                    loading.value = false
                    message.postValue(t.message)
                }
            })

        return users
    }

}