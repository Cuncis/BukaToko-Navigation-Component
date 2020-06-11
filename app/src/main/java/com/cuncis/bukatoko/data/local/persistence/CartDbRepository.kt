package com.cuncis.bukatoko.data.local.persistence

import android.app.Application
import androidx.lifecycle.LiveData

class CartDbRepository(private val application: Application) {

    private val cartDao: CartDao = ShoppingDatabase.getDatabase(application).cartDao()

    suspend fun insertCart(cart: Cart) = cartDao.addToCart(cart)

    fun getAllCarts(): LiveData<Cart> {
        return cartDao.getAllCarts()
    }

}