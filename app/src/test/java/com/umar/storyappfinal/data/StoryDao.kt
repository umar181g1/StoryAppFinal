package com.umar.storyappfinal.data

import androidx.paging.PagingSource
import com.umar.storyappfinal.database.StoryDao
import com.umar.storyappfinal.model.ListStoryItem

class FakeStoryDao : StoryDao {

    private var storyData = mutableListOf<List<ListStoryItem>>()

    override suspend fun insertStory(story: List<ListStoryItem?>?) {
        storyData.add(story as List<ListStoryItem>)
    }

    override fun getAllstories(): PagingSource<Int, ListStoryItem> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        storyData.clear()
    }
}