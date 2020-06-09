package com.cuncis.bukatoko.ui.home.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cuncis.bukatoko.data.api.ApiClient
import com.cuncis.bukatoko.data.model.ProductDetailResponse
import com.cuncis.bukatoko.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailRepository {

    val detailData = MutableLiveData<ProductDetailResponse>()
    val loading = MutableLiveData<Boolean>()

    fun getProductById(id: String): MutableLiveData<ProductDetailResponse> {
        loading.value = true
        ApiClient.theShoppingApi.getProductById(id)
            .enqueue(object : Callback<ProductDetailResponse> {
                override fun onResponse(call: Call<ProductDetailResponse>, response: Response<ProductDetailResponse>) {
                    loading.value = false
                    if (response.isSuccessful) {
                        detailData.postValue(response.body())
                    } else {
                        Log.d(Constants.TAG, "onFailure: ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<ProductDetailResponse>, t: Throwable) {
                    loading.value = false
                    Log.d(Constants.TAG, "onFailure: ${t.localizedMessage}")
                }
            })

        return detailData
    }

}