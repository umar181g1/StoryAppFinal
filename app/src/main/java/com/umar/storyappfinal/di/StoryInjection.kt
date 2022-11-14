package com.umar.storyappfinal.di

import android.content.Context
import com.umar.storyappfinal.database.StoryDatabase
import com.umar.storyappfinal.model.StoryRepository
import com.umar.storyappfinal.network.ApiCall


object StoryInjection {
    fun provider(context: Context): StoryRepository {
        val apiSer = ApiCall.getApiService()
        val database = StoryDatabase.getDatabase(context)
        return StoryRepository(database, apiSer)
    }
}