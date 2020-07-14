package com.cuncis.bukatoko

import android.app.Application
import com.cuncis.bukatoko.di.appModule
import com.cuncis.bukatoko.di.localModule
import com.cuncis.bukatoko.di.repoModule
import com.cuncis.bukatoko.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(
                listOf(
                    appModule,
                    repoModule,
                    localModule,
                    viewModelModule
                )
            )
        }
    }

}