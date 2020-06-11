package com.cuncis.bukatoko.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuncis.bukatoko.data.model.Product
import com.cuncis.bukatoko.util.Constants.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class HomeViewModel : ViewModel() {

    private val repository = HomeRepository()

    val productList = MutableLiveData<List<Product>>()
    val loading = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()

    fun getAllProducts(): MutableLiveData<List<Product>> {
        loading.value = true
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                val response = repository.getAllProducts()
                try {
                    loading.value = false
                    if (response.isSuccessful) {
                        productList.postValue(response.body()?.data)
                    } else {
                        Log.d(TAG, "getAllProducts: ${response.message()}")
                    }
                } catch (e: Exception) {
                    loading.value = false
                    Log.d(TAG, "getAllProducts: ${e.localizedMessage}")
                }
            }
        }

        return productList
    }

//    fun onLoading(): MutableLiveData<Boolean> {
//        return repository.loading
//    }
}