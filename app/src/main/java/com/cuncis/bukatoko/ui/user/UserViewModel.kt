package com.cuncis.bukatoko.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuncis.bukatoko.data.model.Resource
import com.cuncis.bukatoko.data.model.User
import com.cuncis.bukatoko.data.repository.ApiRepoProduct
import kotlinx.coroutines.launch

class UserViewModel(private val apiRepoProduct: ApiRepoProduct) : ViewModel() {

    private val _userData = MutableLiveData<Resource<User.Response>>()
    val userData: MutableLiveData<Resource<User.Response>>
        get() = _userData

    fun login(email: String, password: String) {
        _userData.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                val response = apiRepoProduct.postLogin(email, password)
                _userData.postValue(Resource.success(response))
            } catch (t: Throwable) {
                _userData.postValue(Resource.error(t.message.toString(), null, t))
            }
        }
    }

    fun register(name: String, email: String, password: String) {
        _userData.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                val response = apiRepoProduct.postRegister(name, email, password)
                _userData.postValue(Resource.success(response))
            } catch (t: Throwable) {
                _userData.postValue(Resource.error(t.message.toString(), null, t))
            }
        }
    }

    fun update(userId: Int, name: String, email: String, password: String) {
        _userData.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                val response = apiRepoProduct.postUpdate(userId, name, email, password)
                _userData.postValue(Resource.success(response))
            } catch (t: Throwable) {
                _userData.postValue(Resource.error(t.message.toString(), null, t))
            }
        }
    }

}