package com.cuncis.bukatoko.ui.user

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.cuncis.bukatoko.data.model.Resource
import com.cuncis.bukatoko.data.model.User
import com.cuncis.bukatoko.data.repository.ApiRepoProduct
import com.cuncis.bukatoko.util.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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
class UserViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var apiRepoProduct: ApiRepoProduct

    @Mock
    private lateinit var response: User.Response

    @Mock
    private lateinit var userViewModel: UserViewModel

    @Mock
    private lateinit var userDataObserver: Observer<Resource<User.Response>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userViewModel = UserViewModel(apiRepoProduct)
    }

    @Test
    fun givenSuccessResponse_whenUserLogin() {
        runBlocking {
            // given
            `when`(apiRepoProduct.postLogin("cun@gmail.com", "123456")).thenReturn(response)

            // when
            userViewModel.userData.observeForever(userDataObserver)
            userViewModel.login("cun@gmail.com", "123456")

            // then
            verify(userDataObserver).onChanged(Resource.loading(null))
            verify(userDataObserver).onChanged(Resource.success(response))
            userViewModel.userData.removeObserver(userDataObserver)
        }
    }

    @Test
    fun givenErrorResponse_whenUserLogin() {
        testCoroutineRule.runBlockingTest {
            // given
            val error = Error()
            `when`(apiRepoProduct.postLogin("wrongemail@gmail.com", "123456")).thenThrow(error)

            // when
            userViewModel.userData.observeForever(userDataObserver)
            userViewModel.login("wrongemail@gmail.com", "123456")

            // then
            val throwable = Throwable()
            verify(userDataObserver).onChanged(Resource.loading(null))
            verify(userDataObserver).onChanged(Resource.error(throwable.message.toString(), null, error))
            userViewModel.userData.removeObserver(userDataObserver)
        }
    }

    @Test
    fun givenSuccessResponse_whenUserRegister() {
        testCoroutineRule.runBlockingTest {
            // given
            `when`(apiRepoProduct.postRegister("asd", "asd@gmail.com", "123456")).thenReturn(response)

            // when
            userViewModel.userData.observeForever(userDataObserver)
            userViewModel.register("asd", "asd@gmail.com", "123456")

            // then
            verify(userDataObserver).onChanged(Resource.loading(null))
            verify(userDataObserver).onChanged(Resource.success(response))
            userViewModel.userData.removeObserver(userDataObserver)
        }
    }

    @Test
    fun givenErrorResponse_whenUserRegister() {
        testCoroutineRule.runBlockingTest {
            // given
            val error = Error()
            `when`(apiRepoProduct.postRegister("asd", "asd@gmail.com", "123456")).thenThrow(error)

            // when
            userViewModel.userData.observeForever(userDataObserver)
            userViewModel.register("asd", "asd@gmail.com", "123456")

            // then
            val t = Throwable()
            verify(userDataObserver).onChanged(Resource.loading(null))
            verify(userDataObserver).onChanged(Resource.error(t.message.toString(), null, error))
            userViewModel.userData.removeObserver(userDataObserver)
        }
    }

    @Test
    fun givenSuccessResponse_whenUserUpdateData() {
        testCoroutineRule.runBlockingTest {
            // given
            `when`(apiRepoProduct.postUpdate(52, "cuncis", "cuncc1@gmail.com", "123456")).thenReturn(response)

            // when
            userViewModel.userData.observeForever(userDataObserver)
            userViewModel.update(52, "cuncis", "cuncc1@gmail.com", "123456")

            // then
            verify(userDataObserver).onChanged(Resource.loading(null))
            verify(userDataObserver).onChanged(Resource.success(response))
        }
    }

    fun givenErrorResponse_whenUserUpdateData() {
        testCoroutineRule.runBlockingTest {
            // given
            val error = Error()
            `when`(apiRepoProduct.postUpdate(99, "sample", "sample@gmail.com", "1")).thenThrow(error)

            // when
            userViewModel.userData.observeForever(userDataObserver)
            userViewModel.update(99, "sample", "sample@gmail.com", "1")

            // then
            val t = Throwable()
            verify(userDataObserver).onChanged(Resource.loading(null))
            verify(userDataObserver).onChanged(Resource.error(t.message.toString(), null, error))
            userViewModel.userData.removeObserver(userDataObserver)
        }
    }

}