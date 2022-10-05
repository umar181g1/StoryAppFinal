package com.umar.storyappfinal.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.umar.storyappfinal.model.UserPreference
import com.umar.storyappfinal.network.ApiCall

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object UserInjection {
    fun providePreferences(context: Context): UserPreference {
        val apiSer = ApiCall.getApiService()
        return UserPreference.getInstance(context.dataStore, apiSer)
    }
}