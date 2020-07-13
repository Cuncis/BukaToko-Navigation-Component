package com.cuncis.bukatoko.data.local.persistence

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cuncis.bukatoko.data.model.Cart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(application: Application): AndroidViewModel(application) {

    private val repository = CartDbRepository(application)

//    var cartById = MutableLiveData<Cart>()
//    val cartById: LiveData<Cart>
//        get() = _cartById

    fun getAllCarts(): LiveData<List<Cart>> {
        return repository.getAllCarts()
    }

    fun insertCart(cart: Cart) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertCart(cart)
    }

    fun deleteCart() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteCart()
    }

    fun getCartById(productId: Int): LiveData<Cart> {
        return repository.getCartById(productId)
    }

}