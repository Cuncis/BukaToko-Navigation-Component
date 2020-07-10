package com.cuncis.bukatoko.ui.home

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.cuncis.bukatoko.data.model.Product
import com.cuncis.bukatoko.data.model.Resource
import com.cuncis.bukatoko.data.repository.ApiRepoProduct
import com.cuncis.bukatoko.util.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var app: Application

    @Mock
    private lateinit var viewModel: HomeViewModel

    @Mock
    private lateinit var apiRepoProduct: ApiRepoProduct

    @Mock
    private lateinit var response: Product.Response

    @Mock
    private lateinit var dataProductObserver: Observer<Resource<Product.Response>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = HomeViewModel(apiRepoProduct, app)
    }

    @Test
    fun givenSuccessResponse_whenGetProducts() {
        testCoroutineRule.runBlockingTest {
            // given
            `when`(apiRepoProduct.getAllProduct()).thenReturn(response)

            // when
            viewModel.dataProducts.observeForever(dataProductObserver)
            viewModel.getAllProducts()

            // then
            verify(dataProductObserver).onChanged(Resource.loading(null))
            verify(dataProductObserver).onChanged(Resource.success(response))
            viewModel.dataProducts.removeObserver(dataProductObserver)
        }
    }

    @Test
    fun givenErrorResponse_whenGetProducts() {
        testCoroutineRule.runBlockingTest {
            // given
            val error = Error()
            `when`(apiRepoProduct.getAllProduct()).thenThrow(error)

            // when
            viewModel.dataProducts.observeForever(dataProductObserver)
            viewModel.getAllProducts()

            // then
            val throwable = Throwable()
            verify(dataProductObserver).onChanged(Resource.loading(null))
            verify(dataProductObserver).onChanged(Resource.error(throwable.message.toString(), null, error))
            viewModel.dataProducts.removeObserver(dataProductObserver)
        }
    }
}