package com.cuncis.bukatoko.ui.home.detail

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.cuncis.bukatoko.data.model.Detail
import com.cuncis.bukatoko.data.model.Resource
import com.cuncis.bukatoko.data.repository.ApiRepoProduct
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
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var app: Application

    @Mock
    private lateinit var viewModel: DetailViewModel

    @Mock
    private lateinit var apiRepoProduct: ApiRepoProduct

    @Mock
    private lateinit var response: Detail.Response

    @Mock
    private lateinit var detailProductObserver: Observer<Resource<Detail.Response>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = DetailViewModel(apiRepoProduct, app)
    }

    @Test
    fun giveSuccessResponse_whenGetDetailProduct() {
        testCoroutineRule.runBlockingTest {
            // given
            `when`(apiRepoProduct.getDetailProduct("12")).thenReturn(response)

            // when
            viewModel.detailProduct.observeForever(detailProductObserver)
            viewModel.getDetailProduct("12")

            // then
            verify(detailProductObserver).onChanged(Resource.loading(null))
            verify(detailProductObserver).onChanged(Resource.success(response))
            viewModel.detailProduct.removeObserver(detailProductObserver)
        }
    }

    @Test
    fun giveErrorResponse_whenGetDetailProduct() {
        testCoroutineRule.runBlockingTest {
            // given
            val error = Error()
            `when`(apiRepoProduct.getDetailProduct("-1")).thenThrow(error)

            // when
            viewModel.detailProduct.observeForever(detailProductObserver)
            viewModel.getDetailProduct("-1")

            // then
            val t = Throwable()
            verify(detailProductObserver).onChanged(Resource.loading(null))
            verify(detailProductObserver).onChanged(Resource.error(t.message.toString(), null, t))
            viewModel.detailProduct.removeObserver(detailProductObserver)
        }
    }


}