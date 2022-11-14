package com.umar.storyappfinal.ui.story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.umar.storyappfinal.DataDumy
import com.umar.storyappfinal.MainCoroutineRule
import com.umar.storyappfinal.getOrAwaitValue
import com.umar.storyappfinal.model.ResponseUpload
import com.umar.storyappfinal.model.Result
import com.umar.storyappfinal.model.StoryRepository
import com.umar.storyappfinal.model.UserPreference
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StoryViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val mainDispatcherRules = MainCoroutineRule()

    @Mock
    private lateinit var addStoryViewModel: StoryViewModel
    private val mock = Mockito.mock(StoryRepository::class.java)
    private val mock1 = Mockito.mock(UserPreference::class.java)
    private var dummyMultipart = DataDumy.generateDummyMultipartFile()
    private var dummyDescription = DataDumy.generateDummyRequestBody()
    private val dummyToken = "azhfxrdjgchfgchjvjhfhdgcvcnv"
    private val dummyResponseError = "error"
    private val dummyResponse = ResponseUpload(
        false,
        "success"
    )

    @Before
    fun setUp() {

        addStoryViewModel = StoryViewModel(mock, mock1)


    }

    @Test
    fun `when postStory() is called Should Not Null and Return Success`() {
        val expectdeUplload = MutableLiveData<Result<ResponseUpload>>()
        expectdeUplload.value = Result.Success(dummyResponse)
        Mockito.`when`(mock.addStories("token", dummyMultipart, dummyDescription))
            .thenReturn(
                expectdeUplload
            )
        val actualUplload =
            addStoryViewModel.addStory("token", dummyMultipart, dummyDescription).getOrAwaitValue()
        Mockito.verify(mock).addStories("token", dummyMultipart, dummyDescription)
        Assert.assertNotNull(actualUplload)
        Assert.assertTrue(actualUplload is Result.Success)
        Assert.assertEquals(dummyResponse, (actualUplload as Result.Success).data)
    }

    @Test
    fun `when Network Error Should Return Error`() {
        val expectdeUplload = MutableLiveData<Result<ResponseUpload>>()
        expectdeUplload.value = Result.Error(dummyResponseError)
        Mockito.`when`(mock.addStories("token", dummyMultipart, dummyDescription))
            .thenReturn(
                expectdeUplload
            )
        val actualUplload =
            addStoryViewModel.addStory("token", dummyMultipart, dummyDescription).getOrAwaitValue()
        Mockito.verify(mock).addStories("token", dummyMultipart, dummyDescription)
        assertNotNull(actualUplload)
        assertTrue(actualUplload is Result.Error)

    }

    @Test
    fun `get token successfully`() {
        val expectedToken = flowOf(dummyToken)
        Mockito.`when`(mock1.getToken()).thenReturn(expectedToken)

        val actualToken = addStoryViewModel.getToken().getOrAwaitValue()
        Mockito.verify(mock1).getToken()
        Assert.assertNotNull(actualToken)
        Assert.assertEquals(dummyToken, actualToken)
    }

}