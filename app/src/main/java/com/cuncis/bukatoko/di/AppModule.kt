package com.cuncis.bukatoko.di

import com.cuncis.bukatoko.data.new_api.provideClientProduct
import com.cuncis.bukatoko.data.new_api.provideHttpLoggingInterceptor
import com.cuncis.bukatoko.data.new_api.provideOkHttpClient
import com.cuncis.bukatoko.data.new_repository.ApiRepoProduct
import com.cuncis.bukatoko.ui.home.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory { provideHttpLoggingInterceptor() }
    factory { provideOkHttpClient() }
    factory { provideClientProduct(get()) }
}

val repoModule = module {
    factory { ApiRepoProduct(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get(), androidApplication()) }
}