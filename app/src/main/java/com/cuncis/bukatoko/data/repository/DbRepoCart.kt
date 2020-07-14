package com.cuncis.bukatoko.data.repository

import androidx.lifecycle.LiveData
import com.cuncis.bukatoko.data.local.persistence.CartDao
import com.cuncis.bukatoko.data.model.Cart

class DbRepoCart(private val cartDao: CartDao) {

    suspend fun insertCart(cart: Cart) = cartDao.addToCart(cart)

    fun getAllCarts(): LiveData<List<Cart>> {
        return cartDao.getAllCarts()
    }

    fun getCartById(productId: Int): LiveData<Cart> {
        return cartDao.getCartById(productId)
    }

    suspend fun deleteCart() = cartDao.deleteCart()

}