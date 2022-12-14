package com.umar.storyappfinal.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.umar.storyappfinal.DataDumy
import com.umar.storyappfinal.MainCoroutineRule
import com.umar.storyappfinal.getOrAwaitValue
import com.umar.storyappfinal.model.ResponseLogin
import com.umar.storyappfinal.model.Result
import com.umar.storyappfinal.model.UserPreference
import junit.framework.Assert.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    @Mock
    private lateinit var userPreference: UserPreference
    private lateinit var loginViewModel: LoginViewModel
    private val dummyResult = DataDumy.LoginResponSucses()
    private val dummEmail = "umar@koding.com"
    private val dummYpassword = "1234567"
    private val dummyToken = "azhfxrdjgchfgchjvjhfhdgcvcnv"


    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(userPreference)
    }

    @Test
    fun `when login() is Called Should Return Success and Data`() {
        val expectedResponse = MutableLiveData<Result<ResponseLogin>>()
        expectedResponse.value = Result.Success(dummyResult)
        `when`(userPreference.login(dummEmail,dummYpassword)).thenReturn(expectedResponse)

        val actualResponse = loginViewModel.login(dummEmail, dummYpassword ).getOrAwaitValue()

        Mockito.verify(userPreference).login(dummEmail,dummYpassword)
        assertNotNull(actualResponse)
        assertTrue(true)
        assertEquals(dummyResult, (actualResponse as Result.Success<*>).data)
    }

    @Test
    fun `when Network Error Should Return Error`() {
        val expectedResponse = MutableLiveData<Result<ResponseLogin>>()
        expectedResponse.value = Result.Error("Error")
        `when`(userPreference.login(dummEmail,dummYpassword)).thenReturn(expectedResponse)

        val actualResponse = loginViewModel.login(dummEmail,dummYpassword).getOrAwaitValue()

        Mockito.verify(userPreference).login(dummEmail,dummYpassword)
        assertNotNull(actualResponse)
        assertTrue(actualResponse is Result.Error)
    }

    @Test
     fun `when set token`()= runTest {
        loginViewModel.setToken(dummyToken, true)
         Mockito.verify(userPreference).setToken(dummyToken, true)
    }

    @Test
    fun `when get token sukses`()  {
        val expectedToken = flowOf(dummyToken)
       `when`(userPreference.getToken()).thenReturn(expectedToken)

        val actualToken = loginViewModel.getToken().getOrAwaitValue()
        Mockito.verify(userPreference).getToken()
        Assert.assertNotNull(actualToken)
        Assert.assertEquals(dummyToken, actualToken)
    }

}