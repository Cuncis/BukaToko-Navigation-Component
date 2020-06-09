package com.cuncis.bukatoko.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cuncis.bukatoko.data.api.ApiClient
import com.cuncis.bukatoko.data.model.Product
import com.cuncis.bukatoko.data.model.ProductResponse
import com.cuncis.bukatoko.util.Constants.TAG
import kotlinx.coroutines.GlobalScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {

//    val productList = MutableLiveData<List<Product>>()
//    val loading = MutableLiveData<Boolean>()

//    fun getAllProducts(): MutableLiveData<List<Product>> {
//        loading.value = true
//        ApiClient.theShoppingApi.getAllProducts()
//            .enqueue(object : Callback<ProductResponse> {
//                override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
//                    loading.value = false
//                    if (response.isSuccessful) {
//                        productList.postValue(response.body()?.data)
//                    } else {
//                        Log.d(TAG, "onFailure: ${response.message()}")
//                    }
//                }
//                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
//                    loading.value = false
//                    Log.d(TAG, "onFailure: ${t.localizedMessage}")
//                }
//            })
//
//        return productList
//    }

    suspend fun getAllProducts() = ApiClient.theShoppingApi.getAllProducts()


}