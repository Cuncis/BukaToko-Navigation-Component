package com.cuncis.bukatoko.ui.cart

import android.app.Application
import android.view.View
import android.widget.AdapterView
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cuncis.bukatoko.data.local.persistence.CartDbRepository
import com.cuncis.bukatoko.data.model.Cart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(application: Application): AndroidViewModel(application) {

    private val repository = CartDbRepository(application)

//    private var _spinnerItem = ObservableField<String>()
//    val spinnerItem: ObservableField<String>
//        get() = _spinnerItem

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