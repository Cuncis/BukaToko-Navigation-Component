package com.cuncis.bukatoko.di

import com.cuncis.bukatoko.data.api.*
import com.cuncis.bukatoko.data.local.persistence.CartDao
import com.cuncis.bukatoko.data.local.persistence.ShoppingDatabase
import com.cuncis.bukatoko.data.repository.ApiBase64Repo
import com.cuncis.bukatoko.data.repository.ApiRepoProduct
import com.cuncis.bukatoko.data.repository.ApiRepoRajaOngkir
import com.cuncis.bukatoko.data.repository.DbRepoCart
import com.cuncis.bukatoko.ui.base64.Base64ViewModel
import com.cuncis.bukatoko.ui.cart.CartViewModel
import com.cuncis.bukatoko.ui.cart.checkout.CheckoutViewModel
import com.cuncis.bukatoko.ui.home.HomeViewModel
import com.cuncis.bukatoko.ui.home.detail.DetailViewModel
import com.cuncis.bukatoko.ui.transaction.detail.TransactionDetailViewModel
import com.cuncis.bukatoko.ui.transaction.tab.TransactionViewModel
import com.cuncis.bukatoko.ui.transaction.upload.TransactionUploadViewModel
import com.cuncis.bukatoko.ui.user.UserViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory { provideHttpLoggingInterceptor() }
    factory { provideOkHttpClient() }
    factory { provideClientProduct(get()) }
    factory { provideClientRajaOngkir(get()) }
    factory { provideClientBase64(get()) }
}

val repoModule = module {
    factory { ApiRepoProduct(get()) }
    factory { DbRepoCart(get()) }
    factory { ApiRepoRajaOngkir(get()) }
    factory { ApiBase64Repo(get()) }
}

val localModule = module {
    fun cartDao(database: ShoppingDatabase): CartDao = database.cartDao()
    single { ShoppingDatabase.getDatabase(androidApplication()) }
    single { cartDao(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get(), androidApplication()) }
    viewModel { DetailViewModel(get(), androidApplication()) }
    viewModel { UserViewModel(get()) }
    viewModel { CartViewModel(get(), androidApplication()) }
    viewModel { CheckoutViewModel(get(), get()) }
    viewModel { TransactionViewModel(get()) }
    viewModel { TransactionUploadViewModel(get()) }
    viewModel {
        TransactionDetailViewModel(
            get()
        )
    }
    viewModel { Base64ViewModel(get()) }
}