package com.cuncis.bukatoko.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuncis.bukatoko.data.model.Resource
import com.cuncis.bukatoko.data.new_model.User
import com.cuncis.bukatoko.data.new_repository.ApiRepoProduct
import kotlinx.coroutines.launch

class UserViewModel(private val apiRepoProduct: ApiRepoProduct) : ViewModel() {

    private val repository = UserRepository()
    private val _userData = MutableLiveData<Resource<User.Response>>()
    val userData: MutableLiveData<Resource<User.Response>>
        get() = _userData

    fun login(email: String, password: String) {
        userData.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                val response = apiRepoProduct.postLogin(email, password)
                userData.postValue(Resource.success(response))
            } catch (t: Throwable) {
                userData.postValue(Resource.error(t.message.toString(), null, t))
            }
        }
    }

    fun register(name: String, email: String, password: String): MutableLiveData<User.Response> {
        return repository.register(name, email, password)
    }

    fun updateUser(userId: Int, name: String, email: String, password: String): MutableLiveData<User.Response> {
        return repository.update(userId, name, email, password)
    }

    fun onLoading(): MutableLiveData<Boolean> {
        return repository.loading
    }

    fun getMessage(): MutableLiveData<String> {
        return repository.message
    }

}