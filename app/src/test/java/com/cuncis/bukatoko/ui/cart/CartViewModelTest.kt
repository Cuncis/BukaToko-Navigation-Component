package com.cuncis.bukatoko.ui.cart

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.cuncis.bukatoko.data.local.persistence.CartDao
import com.cuncis.bukatoko.data.model.Cart
import com.cuncis.bukatoko.data.model.Resource
import com.cuncis.bukatoko.data.model.User
import com.cuncis.bukatoko.data.repository.ApiRepoProduct
import com.cuncis.bukatoko.data.repository.DbRepoCart
import com.cuncis.bukatoko.ui.user.UserViewModel
import com.cuncis.bukatoko.util.Repository
import com.cuncis.bukatoko.util.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CartViewModelTest {

//    @get:Rule
//    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
//
//    @get:Rule
//    val testCoroutineRule = TestCoroutineRule()
//
//    @Mock
//    private lateinit var apiRepoProduct: ApiRepoProduct
//
//    @Mock
//    private lateinit var response: User.Response
//
//    @Mock
//    private lateinit var userViewModel: UserViewModel
//
//    @Mock
//    private lateinit var userDataObserver: Observer<Resource<User.Response>>

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var app: Application

    @Mock
    private lateinit var dbRepoCart: DbRepoCart

    @Mock
    private lateinit var cartViewModel: CartViewModel

    @Mock
    private lateinit var cartDao: CartDao

    @Mock
    private lateinit var cartDataObserver: Observer<Resource<Cart>>

    val mockCart = mock(Repository::class.java)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        cartViewModel = CartViewModel(dbRepoCart, app)
    }

    @Test
    fun givenSuccessResponse_whenGetAllCart() {
        `when`(dbRepoCart.getAllCarts()).thenReturn(mockCart.cartDao().getAllCarts())
    }

}











