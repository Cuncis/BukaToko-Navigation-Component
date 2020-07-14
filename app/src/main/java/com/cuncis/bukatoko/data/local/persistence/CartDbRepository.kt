package com.cuncis.bukatoko.data.local.persistence

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cuncis.bukatoko.data.model.Cart

class CartDbRepository(application: Application) {

    private val cartDao: CartDao = ShoppingDatabase.getDatabase(application).cartDao()

    suspend fun insertCart(cart: Cart) = cartDao.addToCart(cart)

    fun getAllCarts(): LiveData<List<Cart>> {
        return cartDao.getAllCarts()
    }

    fun getCartById(productId: Int): LiveData<Cart> {
        return cartDao.getCartById(productId)
    }

    suspend fun deleteCart() = cartDao.deleteCart()

//    fun deleteCartById(productId: Int): LiveData<Cart> {
//        return cartDao.deleteCartById(productId)
//    }

}