package com.cuncis.bukatoko.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cuncis.bukatoko.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        GlobalScope.launch {
            delay(2000)
            val intent = Intent(this@SplashscreenActivity, ShoppingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}