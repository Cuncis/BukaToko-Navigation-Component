package com.cuncis.bukatoko.data.local.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Cart::class], version = 1)
abstract class ShoppingDatabase: RoomDatabase() {

    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        var INSTANCE: ShoppingDatabase? = null

        fun getDatabase(context: Context): ShoppingDatabase {
            val tempInstace = INSTANCE

            if (tempInstace != null) {
                return tempInstace
            }

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShoppingDatabase::class.java,
                    "shopping.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}