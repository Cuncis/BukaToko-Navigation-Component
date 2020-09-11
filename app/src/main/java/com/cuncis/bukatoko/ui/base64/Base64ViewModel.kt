package com.cuncis.bukatoko.ui.base64

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuncis.bukatoko.data.model.Base64Data
import com.cuncis.bukatoko.data.model.Resource
import com.cuncis.bukatoko.data.repository.ApiBase64Repo
import kotlinx.coroutines.launch

class Base64ViewModel(private val repo: ApiBase64Repo) : ViewModel() {

    private val _data = MutableLiveData<Resource<Base64Data.Response>>()
    val data: LiveData<Resource<Base64Data.Response>>
        get() = _data

    fun getBase64Data() = viewModelScope.launch {
        _data.postValue(Resource.loading(null))
        try {
            val response = repo.getBase64Data()
            _data.postValue(Resource.success(response))
        } catch (t: Throwable) {
            _data.postValue(Resource.error(t.message.toString(), null, t))
        }
    }

}