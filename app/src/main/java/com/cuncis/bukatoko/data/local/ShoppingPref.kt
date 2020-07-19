package com.cuncis.bukatoko.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.cuncis.bukatoko.data.model.UserData
import com.cuncis.bukatoko.util.Constants.EMAIL_KEY
import com.cuncis.bukatoko.util.Constants.ID_USER_KEY
import com.cuncis.bukatoko.util.Constants.PASSWORD_KEY
import com.cuncis.bukatoko.util.Constants.PREF_NAME
import com.cuncis.bukatoko.util.Constants.USERNAME_KEY

class ShoppingPref {
    companion object {

        private fun getSharedPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        }

        fun setUsername(context: Context, userId: Int, username: String, email: String, password: String) {
            val editor = getSharedPreferences(context).edit()
            editor.putInt(ID_USER_KEY, userId)
            editor.putString(USERNAME_KEY, username)
            editor.putString(EMAIL_KEY, email)
            editor.putString(PASSWORD_KEY, password)
            editor.apply()
        }

        fun getUserData(context: Context): UserData {
            val pref = getSharedPreferences(context)
            return UserData(
                pref.getInt(ID_USER_KEY, 0),
                pref.getString(USERNAME_KEY, "").toString(),
                pref.getString(EMAIL_KEY, "").toString(),
                pref.getString(PASSWORD_KEY, "").toString()
            )
        }

        fun getUsername(context: Context): String {
            return getSharedPreferences(context).getString(USERNAME_KEY, "").toString()
        }

        fun getUserId(context: Context): Int {
            return getSharedPreferences(context).getInt(ID_USER_KEY, 0)
        }

        fun clearPref(context: Context) {
            val editor = getSharedPreferences(context).edit()
            editor.clear()
            editor.apply()
        }
    }
}