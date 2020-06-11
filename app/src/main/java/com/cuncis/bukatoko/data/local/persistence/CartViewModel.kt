package com.cuncis.bukatoko.data.local.persistence

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(application: Application): AndroidViewModel(application) {

    private val repository = CartDbRepository(application)

    fun getAllCarts(): LiveData<Cart> {
        return repository.getAllCarts()
    }

    fun insertCart(cart: Cart) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertCart(cart)
    }

}