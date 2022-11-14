package com.umar.storyappfinal.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.umar.storyappfinal.DataDumy
import com.umar.storyappfinal.MainCoroutineRule
import com.umar.storyappfinal.adapter.StoryAdapter
import com.umar.storyappfinal.getOrAwaitValue
import com.umar.storyappfinal.model.ListStoryItem
import com.umar.storyappfinal.model.StoryRepository
import com.umar.storyappfinal.model.UserPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainCoroutineRule()

    private lateinit var mainViewModel: MainViewModel
    private val mock = Mockito.mock(StoryRepository::class.java)
    private val mock1 = Mockito.mock(UserPreference::class.java)

    private val dummyToken = "azhfxrdjgchfgchjvjhfhdgcvcnv"
    private val dummySession = true

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(mock1, mock)
    }

    @Test
    fun `set logout successfully`() = mainDispatcherRules.runBlockingTest {
        mainViewModel.logout()
        Mockito.verify(mock1).logout()
    }

    @Test
    fun `get token successfully`() {
        val expectedToken = flowOf(dummyToken)
        Mockito.`when`(mock1.getToken()).thenReturn(expectedToken)

        val actualToken = mainViewModel.getToken().getOrAwaitValue()
        Mockito.verify(mock1).getToken()
        Assert.assertNotNull(actualToken)
        Assert.assertEquals(dummyToken, actualToken)
    }



    @Test
    fun `when Get Story Should Not Null`() = runTest {
        val dummyQuote = DataDumy.generateDummyStoryListResponse()
        val data: PagingData<ListStoryItem> = StoryPagingSource.snapshot(dummyQuote)
        val expectedQuote = MutableLiveData<PagingData<ListStoryItem>>()
        expectedQuote.value = data
        Mockito.`when`(mock.getStories(dummyToken)).thenReturn(expectedQuote)

        val mainViewModel = MainViewModel(mock1, mock)
        val actualQuote: PagingData<ListStoryItem> =
            mainViewModel.getStories(dummyToken).getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyQuote, differ.snapshot())
        Assert.assertEquals(dummyQuote.size, differ.snapshot().size)
        Assert.assertEquals(dummyQuote[0].name, differ.snapshot()[0]?.name)
    }

    @Test
    fun `get session login successfully`() {
        val expectedIslogin = flowOf(dummySession)
        Mockito.`when`(mock1.isLogin()).thenReturn(expectedIslogin)

        val actualIslogin = mainViewModel.isLogin().getOrAwaitValue()
        Mockito.verify(mock1).isLogin()
        Assert.assertNotNull(actualIslogin)
        Assert.assertEquals(dummySession, actualIslogin)
    }
}



class StoryPagingSource : PagingSource<Int, LiveData<List<ListStoryItem>>>() {
    companion object {
        fun snapshot(items: List<ListStoryItem>): PagingData<ListStoryItem> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<ListStoryItem>>>): Int? {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ListStoryItem>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }

}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}

