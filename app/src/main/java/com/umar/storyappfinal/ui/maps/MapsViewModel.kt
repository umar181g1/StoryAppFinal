package com.umar.storyappfinal.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.umar.storyappfinal.model.StoryRepository
import com.umar.storyappfinal.model.UserPreference

class MapsViewModel(
    private val storyRepository: StoryRepository,
    private val userPreference: UserPreference
) : ViewModel() {
    fun getStoriesWithMaps(token: String) = storyRepository.getMaps(token)
    fun getToken(): LiveData<String> {
        return userPreference.getToken().asLiveData()
    }
}