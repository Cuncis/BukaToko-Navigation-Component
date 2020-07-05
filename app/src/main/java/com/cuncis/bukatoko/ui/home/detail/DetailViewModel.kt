package com.cuncis.bukatoko.ui.home.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cuncis.bukatoko.data.model.Resource
import com.cuncis.bukatoko.data.new_model.Detail
import com.cuncis.bukatoko.data.new_repository.ApiRepoProduct
import kotlinx.coroutines.launch

class DetailViewModel(private val apiRepoProduct: ApiRepoProduct, application: Application)
    : AndroidViewModel(application) {

    private val _detailProduct = MutableLiveData<Resource<Detail.Response>>()
    val detailProduct: MutableLiveData<Resource<Detail.Response>>
        get() = _detailProduct

    fun getDetailProduct(productId: String) {
        _detailProduct.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                val response = apiRepoProduct.getDetailProduct(productId)
                _detailProduct.postValue(Resource.success(response))
            } catch (t: Throwable) {
                _detailProduct.postValue(Resource.error(t.message.toString(), null, t))
            }
        }
    }

}