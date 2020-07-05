package com.cuncis.bukatoko.di

import com.cuncis.bukatoko.data.api.provideClientProduct
import com.cuncis.bukatoko.data.api.provideHttpLoggingInterceptor
import com.cuncis.bukatoko.data.api.provideOkHttpClient
import com.cuncis.bukatoko.data.repository.ApiRepoProduct
import com.cuncis.bukatoko.ui.home.HomeViewModel
import com.cuncis.bukatoko.ui.home.detail.DetailViewModel
import com.cuncis.bukatoko.ui.user.UserViewModel
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
    viewModel { DetailViewModel(get(), androidApplication()) }
    viewModel { UserViewModel(get()) }
}