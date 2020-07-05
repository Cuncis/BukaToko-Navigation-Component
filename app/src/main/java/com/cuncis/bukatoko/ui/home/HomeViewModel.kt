package com.cuncis.bukatoko.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cuncis.bukatoko.data.model.Resource
import com.cuncis.bukatoko.data.model.Product
import com.cuncis.bukatoko.data.repository.ApiRepoProduct
import kotlinx.coroutines.launch

class HomeViewModel(private val apiRepoProduct: ApiRepoProduct, application: Application)
    : AndroidViewModel(application) {

    private val _dataProducts = MutableLiveData<Resource<Product.Response>>()
    val dataProducts: MutableLiveData<Resource<Product.Response>>
        get() = _dataProducts

    fun getAllProducts() {
        _dataProducts.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                val response = apiRepoProduct.getAllProduct()
                _dataProducts.postValue(Resource.success(response))
            } catch (t: Throwable) {
                _dataProducts.postValue(Resource.error(t.message.toString(), null, t))
            }
        }
    }

}