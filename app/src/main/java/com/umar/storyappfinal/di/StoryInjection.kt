package com.umar.storyappfinal.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.umar.storyappfinal.database.StoryDatabase
import com.umar.storyappfinal.model.StoryRepository
import com.umar.storyappfinal.network.ApiCall


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object StoryInjection {
    fun provider(context: Context): StoryRepository {
        val apiSer = ApiCall.getApiService()
        val database = StoryDatabase.getDatabase(context)
        return StoryRepository(database, apiSer, context.dataStore)
    }
}