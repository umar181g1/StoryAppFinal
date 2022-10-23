package com.umar.storyappfinal.data

import com.umar.storyappfinal.DataDumy
import com.umar.storyappfinal.model.ResponseAll
import com.umar.storyappfinal.model.ResponseLogin
import com.umar.storyappfinal.model.ResponseRegister
import com.umar.storyappfinal.model.ResponseUpload
import com.umar.storyappfinal.network.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FakeApi : ApiService {
    private val dummyStoryResponse = DataDumy.generateDummyStoriesResponse()
    private val dummyStoryUpRes = DataDumy.generateDummyStoryyResponseSuccess()
    private val dummyApiResponseSuccess = DataDumy.generateDummyApiResponseSuccess()
    private val dummyLoginResponseSuccess =  DataDumy.generateDummyLoginResponseSuccess()

    override suspend fun login(email: String, password: String): ResponseLogin {
        return dummyLoginResponseSuccess
    }

    override suspend fun register(name: String, email: String, password: String): ResponseRegister {
        return dummyApiResponseSuccess
    }

    override suspend fun getStories(
        token: String,
        page: Int?,
        size: Int?,
        location: Int
    ): ResponseAll {
        return dummyStoryResponse
    }

    override suspend fun addStory(
        Authorization: String,
        file: MultipartBody.Part,
        description: RequestBody
    ): ResponseUpload {
    return  dummyStoryUpRes
    }
}