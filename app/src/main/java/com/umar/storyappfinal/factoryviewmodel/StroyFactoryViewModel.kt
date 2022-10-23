package com.umar.storyappfinal.factoryviewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.umar.storyappfinal.di.StoryInjection
import com.umar.storyappfinal.di.UserInjection
import com.umar.storyappfinal.model.StoryRepository
import com.umar.storyappfinal.model.UserPreference
import com.umar.storyappfinal.ui.main.MainViewModel
import com.umar.storyappfinal.ui.maps.MapsViewModel
import com.umar.storyappfinal.ui.story.StoryViewModel

class StroyFactoryViewModel private constructor(
    private val userPreference: UserPreference,
    private val storyRepository: StoryRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(userPreference, storyRepository) as T
            }
            modelClass.isAssignableFrom(StoryViewModel::class.java) -> {
                StoryViewModel(storyRepository,userPreference) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(storyRepository,userPreference) as T
            }
            else -> {
                throw  IllegalArgumentException("uknow viewmodel class:" + modelClass.name)
            }
        }
    }

    companion object {
        private var instance: StroyFactoryViewModel? = null
        fun getInstance(context: Context): StroyFactoryViewModel =
            instance ?: synchronized(this) {
                instance ?: StroyFactoryViewModel(
                    UserInjection.providePreferences(context),
                    StoryInjection.provider(context)

                )
            }.also { instance = it }
    }
}