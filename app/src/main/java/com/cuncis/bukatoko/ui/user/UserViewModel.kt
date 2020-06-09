package com.cuncis.bukatoko.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cuncis.bukatoko.data.model.Users

class UserViewModel : ViewModel() {

    private val repository = UserRepository()

    fun login(email: String, password: String): MutableLiveData<Users> {
        return repository.login(email, password)
    }

    fun register(name: String, email: String, password: String): MutableLiveData<Users> {
        return repository.register(name, email, password)
    }

    fun updateUser(userId: Int, name: String, email: String, password: String): MutableLiveData<Users> {
        return repository.update(userId, name, email, password)
    }

    fun onLoading(): MutableLiveData<Boolean> {
        return repository.loading
    }

    fun getMessage(): MutableLiveData<String> {
        return repository.message
    }

}