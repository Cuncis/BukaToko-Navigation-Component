package com.cuncis.bukatoko.ui.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cuncis.bukatoko.data.model.Cart
import com.cuncis.bukatoko.data.repository.DbRepoCart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(private val repository: DbRepoCart, application: Application)
    : AndroidViewModel(application) {

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

    fun deleteCartById(productId: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteCartById(productId)
    }


}