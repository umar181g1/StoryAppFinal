package com.umar.storyappfinal.model

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.umar.storyappfinal.database.StoryDatabase
import com.umar.storyappfinal.database.StoryRemoteMediator
import com.umar.storyappfinal.network.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository(
    private val storyDatabase: StoryDatabase,
    private val apiService: ApiService,
    dataStore: DataStore<Preferences>,
) {
    fun getStories(token: String): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService, token),
            pagingSourceFactory = {
                storyDatabase.databseDao().getAllstories()
            }
        ).liveData
    }

    fun addStories(
        token: String,
        photo: MultipartBody.Part,
        desc: RequestBody
    ): LiveData<Result<ResponseUpload>> = liveData {
        emit(Result.Loading)
        try {
            val result = apiService.addStory("Bearer $token", photo, desc)
            emit(Result.Success(result))
        } catch (e: Exception) {
            Log.d("StoryRepository", "addStories: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getMaps(token: String): LiveData<Result<ResponseAll>> = liveData {
        emit(Result.Loading)
        try {
            val result = apiService.getStories("Bearer $token", location = 1)
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: StoryRepository? = null

        fun getInstance(
            storyDatabase: StoryDatabase,
            dataStore: DataStore<Preferences>,
            apiService: ApiService
        ): StoryRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = StoryRepository(storyDatabase, apiService, dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}