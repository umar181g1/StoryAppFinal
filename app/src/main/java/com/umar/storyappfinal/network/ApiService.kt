package com.umar.storyappfinal.network

import com.umar.storyappfinal.model.ResponseAll
import com.umar.storyappfinal.model.ResponseLogin
import com.umar.storyappfinal.model.ResponseRegister
import com.umar.storyappfinal.model.ResponseUpload
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): ResponseLogin

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): ResponseRegister


    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("location") location: Int = 0
    ): ResponseAll

    @Multipart
    @POST("stories")
    suspend fun addStory(
        @Header("Authorization") Authorization: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): ResponseUpload
}