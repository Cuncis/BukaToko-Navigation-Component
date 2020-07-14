package com.cuncis.bukatoko.data.local.persistence

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.cuncis.bukatoko.data.model.Cart

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cart: Cart)

    @Query("SELECT * FROM cart_table")
    fun getAllCarts(): LiveData<List<Cart>>

    @Query("SELECT * FROM cart_table WHERE product_id = :productId")
    fun getCartById(productId: Int): LiveData<Cart>

    @Query("DELETE FROM cart_table")
    suspend fun deleteCart()

    @Query("DELETE FROM cart_table WHERE product_id = :productId")
    suspend fun deleteCartById(productId: Int)

}