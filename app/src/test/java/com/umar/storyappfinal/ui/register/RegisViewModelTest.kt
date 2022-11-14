package com.umar.storyappfinal.ui.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.umar.storyappfinal.DataDumy
import com.umar.storyappfinal.getOrAwaitValue
import com.umar.storyappfinal.model.ResponseRegister
import com.umar.storyappfinal.model.Result
import com.umar.storyappfinal.model.UserPreference
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisViewModelTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userPreference: UserPreference
    private lateinit var registerViewModel: RegisViewModel
    private val dummy = DataDumy.ApiResponseSucces()
    private val dummynama = "umar"
    private val dummyemail = "umar@ok.com"
    private val dummypassword = "12345678"


    @Before
    fun setUp() {
        registerViewModel = RegisViewModel(userPreference)
    }

    @Test
    fun `when register() is Called Should Not Null and Return Success`() {
        val expectedResponse = MutableLiveData<Result<ResponseRegister>>()
        expectedResponse.value = Result.Success(dummy)
        Mockito.`when`(userPreference.register(dummynama,dummyemail,dummypassword)).thenReturn(expectedResponse)

        val actualResponse = registerViewModel.register(dummynama,dummyemail,dummypassword).getOrAwaitValue()

        Mockito.verify(userPreference).register(dummynama,dummyemail,dummypassword)
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Result.Success)
        Assert.assertEquals(dummy, (actualResponse as Result.Success).data)
    }

    @Test
    fun `when Network Error Should Return Error`() {
        val expectedResponse = MutableLiveData<Result<ResponseRegister>>()
        expectedResponse.value = Result.Error("Error")
        Mockito.`when`(userPreference.register(dummynama,dummyemail,dummypassword)).thenReturn(expectedResponse)

        val actualResponse = registerViewModel.register(dummynama,dummyemail,dummypassword).getOrAwaitValue()

        Mockito.verify(userPreference).register(dummynama,dummyemail,dummypassword)
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Result.Error)
    }


}