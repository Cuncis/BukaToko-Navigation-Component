package com.cuncis.bukatoko.ui.home.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cuncis.bukatoko.data.model.ProductDetailResponse

class DetailViewModel : ViewModel() {

    private val repository = DetailRepository()

    fun getProductById(id: String): MutableLiveData<ProductDetailResponse> {
        return repository.getProductById(id)
    }

    fun onLoading(): MutableLiveData<Boolean> {
        return repository.loading
    }

    fun getMessage(): MutableLiveData<String> {
        return repository.message
    }

}