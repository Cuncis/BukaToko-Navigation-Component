package com.cuncis.bukatoko.util


import com.cuncis.bukatoko.data.local.persistence.CartDao
import com.cuncis.bukatoko.data.local.persistence.ShoppingDatabase

open class Repository(private val roomDatabase: ShoppingDatabase) {
    open fun cartDao(): CartDao = roomDatabase.cartDao()
}