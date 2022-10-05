package com.umar.storyappfinal.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.umar.storyappfinal.model.StoryRepository
import com.umar.storyappfinal.model.UserPreference
import kotlinx.coroutines.launch

class MainViewModel(
    private val userPreference: UserPreference,
    private val storyRepository: StoryRepository
) : ViewModel() {
    fun getToken(): LiveData<String> {
        return userPreference.getToken().asLiveData()
    }

    fun isLogin(): LiveData<Boolean> {
        return userPreference.isLogin().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            userPreference.logout()
        }
    }

    fun getStories(token: String) = storyRepository.getStories(token)
}
