package com.cuncis.bukatoko.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuncis.bukatoko.data.model.Product
import com.cuncis.bukatoko.data.model.ProductResponse
import com.cuncis.bukatoko.util.Constants.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    var productList = MutableLiveData<List<Product>>()

    fun getAllProducts(): MutableLiveData<List<Product>> {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllProducts()
            try {
                if (response.isSuccessful) {
                    val products = repository.getAllProducts()
                    productList.postValue(products.body()?.data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return productList
    }

}