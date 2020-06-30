package com.cuncis.bukatoko.data.local.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cart: Cart)

    @Query("SELECT * FROM cart_table")
    fun getAllCarts(): LiveData<Cart>

}