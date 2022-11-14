package com.umar.storyappfinal.ui.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.umar.storyappfinal.DataDumy
import com.umar.storyappfinal.MainCoroutineRule
import com.umar.storyappfinal.getOrAwaitValue
import com.umar.storyappfinal.model.ResponseAll
import com.umar.storyappfinal.model.Result
import com.umar.storyappfinal.model.StoryRepository
import com.umar.storyappfinal.model.UserPreference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainCoroutineRule()


    @Mock
    private lateinit var storyRepository: StoryRepository
    private val mock1 = Mockito.mock(UserPreference::class.java)
    private lateinit var viewModel: MapsViewModel
    private val dummyToken = "AIzaSyDMZjGtT2uJD4E7qaOUzGJu5F7RFkvhj98"
    private var dummyMaps = DataDumy.generateDummyMapsEntity()


    @Before
    fun setUp() {
        viewModel = MapsViewModel(storyRepository,mock1)
    }

    @Test
    fun `when getStories() is Called Should Not Null and Return Success`() {
        val story = MutableLiveData<Result<ResponseAll>>()
        story.value = Result.Success(dummyMaps)
        `when`(storyRepository.getMaps(dummyToken)).thenReturn(story)

        val actualStory = viewModel.getStoriesWithMaps(dummyToken).getOrAwaitValue()

        Assert.assertNotNull(actualStory)
        Assert.assertTrue(actualStory is Result.Success<*>)
        Assert.assertEquals(dummyMaps, (actualStory as Result.Success<*>).data)
    }

    @Test
    fun `when Network Error Should Return Error`() {
        val expectedStory = MutableLiveData<Result<ResponseAll>>()
        expectedStory.value = Result.Error("Error")
        `when`(storyRepository.getMaps(dummyToken)).thenReturn(expectedStory)

        val actualStory = viewModel.getStoriesWithMaps(dummyToken).getOrAwaitValue()

        Assert.assertNotNull(actualStory)
        Assert.assertTrue(actualStory is Result.Error)
    }

    @Test
    fun `get token successfully`() {
        val expectedToken = flowOf(dummyToken)
        `when`(mock1.getToken()).thenReturn(expectedToken)

        val actualToken = viewModel.getToken().getOrAwaitValue()
        Mockito.verify(mock1).getToken()
        Assert.assertNotNull(actualToken)
        Assert.assertEquals(dummyToken, actualToken)
    }

}