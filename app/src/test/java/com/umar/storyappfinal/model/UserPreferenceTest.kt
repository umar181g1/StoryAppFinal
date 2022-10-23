package com.umar.storyappfinal.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.umar.storyappfinal.DataDumy
import com.umar.storyappfinal.MainCoroutineRule
import com.umar.storyappfinal.data.FakeApi
import com.umar.storyappfinal.network.ApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserPreferenceTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var userRepository: UserPreference
    private var dummmyName = "umar"
    private val dummEmail = "umar@koding.com"
    private val dummYpassword = "1234567"


    @Before
    fun setup() {
        apiService = FakeApi()
        userRepository = UserPreference.getInstance(dataStore, apiService)
    }

    @Test
    fun `when login response Should Not Null`() = mainCoroutineRule.runBlockingTest {
        val expectedResponse = DataDumy.generateDummyLoginResponseSuccess()
        val actualResponse = apiService.login(dummEmail, dummYpassword)
        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(actualResponse, expectedResponse)
    }

    @Test
    fun `when register response Should Not Null`() = mainCoroutineRule.runBlockingTest {
        val expectedResponse = DataDumy.ApiResponseSucces()
        val actualResponse = apiService.register(dummmyName, dummEmail, dummYpassword)
        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(actualResponse, expectedResponse)
    }
}