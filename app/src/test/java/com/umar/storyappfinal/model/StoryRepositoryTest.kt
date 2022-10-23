package com.umar.storyappfinal.model

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.room.Room
import com.umar.storyappfinal.DataDumy
import com.umar.storyappfinal.MainCoroutineRule
import com.umar.storyappfinal.adapter.StoryAdapter
import com.umar.storyappfinal.data.FakeApi
import com.umar.storyappfinal.data.FakeStoryDao
import com.umar.storyappfinal.database.StoryDao
import com.umar.storyappfinal.database.StoryDatabase
import com.umar.storyappfinal.getOrAwaitValue
import com.umar.storyappfinal.network.ApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryRepositoryTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var apiService: ApiService
    private lateinit var storyDatabase: StoryDatabase
    private val context = mock(Context::class.java)


    @Mock
    private lateinit var storyDao: StoryDao
    private var dummyMultipart = DataDumy.generateDummyMultipartFile()
    private var dummyDescription = DataDumy.generateDummyRequestBody()

    @Before
    fun setUp() {
        apiService = FakeApi()
        storyDao = FakeStoryDao()
        storyDatabase = Room.inMemoryDatabaseBuilder(context, StoryDatabase::class.java).build()
        storyRepository = StoryRepository(storyDatabase, apiService)

    }

    @Test
    fun `when getStoryMap() is called Should Not Null`() = runTest {
        val expectedStory = DataDumy.generateDummyStoriesResponse()
        val actualStory = apiService.getStories("Token")
        Assert.assertNotNull(actualStory)
        Assert.assertEquals(expectedStory.listStory.size, actualStory.listStory.size)
    }

    @Test
    fun `when postStory() is called Should Not Null`() = runTest {
        val expectedResponse = DataDumy.generateDummyStoryyResponseSuccess()
        val actualResponse = apiService.addStory("Token", dummyMultipart, dummyDescription)
        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun `when Get Quote Should Not Null and Return Success`() = runTest {
        val dummyQuote = DataDumy.generateDummyListStory()
        val expectedStories = MutableLiveData<PagingData<ListStoryItem>>()
        expectedStories.value = PagedTestDatSiurce.snapshot(dummyQuote)

        Mockito.`when`(storyRepository.getStories("token")).thenReturn(expectedStories)
        val noopListUpdateCallback = object : ListUpdateCallback {
            override fun onInserted(position: Int, count: Int) {}
            override fun onRemoved(position: Int, count: Int) {}
            override fun onMoved(fromPosition: Int, toPosition: Int) {}
            override fun onChanged(position: Int, count: Int, payload: Any?) {}
        }
        val actualStories = storyRepository.getStories("token").getOrAwaitValue()
        val storyDiffer = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = mainCoroutineRule.dispatcher,
            workerDispatcher = mainCoroutineRule.dispatcher
        )
        storyDiffer.submitData(actualStories)
        Assert.assertNotNull(storyDiffer.snapshot())
        Assert.assertEquals(dummyQuote.size, storyDiffer.snapshot().size)
    }

}



