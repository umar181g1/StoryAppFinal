package com.umar.storyappfinal.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.umar.storyappfinal.model.StoryRepository
import com.umar.storyappfinal.model.UserPreference
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryViewModel(
    private var storyRepository: StoryRepository,
    private val userPreference: UserPreference,

    ) : ViewModel() {
    fun getToken(): LiveData<String> {
        return userPreference.getToken().asLiveData()
    }
    fun addStory(token: String, photo: MultipartBody.Part, desc: RequestBody) =
        storyRepository.addStories(token, photo, desc)
}