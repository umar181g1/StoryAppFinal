package com.umar.storyappfinal

import com.umar.storyappfinal.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


object DataDumy {
   private fun  generateDummyLoginResult() : LoginResult {
        return LoginResult(
            "umar",
            "1234",
            "token"
        )
    }

     fun LoginResponSucses(): ResponseLogin {
        return ResponseLogin(
            loginResult =  generateDummyLoginResult(),
            error = false,
            message = "Success"
        )
    }

    fun ApiResponseSucces(): ResponseRegister {
        return ResponseRegister(
            false,
            "success"
        )
    }

    fun generateDummyLoginResponseSuccess(): ResponseLogin {
        return ResponseLogin(
            loginResult = generateDummyLoginResult(),
            error = false,
            message = "Success"
        )
    }

    fun generateDummyStoriesResponse(): ResponseAll {
        return ResponseAll(generateDummyStoryListResponse(), false, "Success")
    }


    fun generateDummyApiResponseSuccess(): ResponseRegister {
        return ResponseRegister(
            false,
            "success"
        )
    }

    fun generateDummyStoryyResponseSuccess(): ResponseUpload {
        return ResponseUpload(
            false,
            "success"
        )
    }

    fun generateDummyStoryListResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val quote = ListStoryItem(
                i.toString(),
                "author + $i",
                "quote $i",
            )
            items.add(quote)
        }
        return items
    }

    fun generateDummyListStory(): List<ListStoryItem> {
        val stories = arrayListOf<ListStoryItem>()

        for (i in 0 until 10) {
            val story = ListStoryItem(
                "story-FvU4u0Vp2S3PMsFg",
                "Dimas",
                "2022-01-08T06:34:18.598Z",
                "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                "Lorem Ipsum",
                -16.002,
                -10.212
            )

            stories.add(story)
        }
        return stories

    }


    fun generateDummyMapsEntity(): ResponseAll {
        val stories = arrayListOf<ListStoryItem>()

        for (i in 0 until 10) {
            val story = ListStoryItem(
                "story-FvU4u0Vp2S3PMsFg",
                "Dimas",
                "2022-01-08T06:34:18.598Z",
                "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                "Lorem Ipsum",
                -16.002,
                -10.212
            )

            stories.add(story)
        }
        return ResponseAll(stories, false, "Stories fetched successfully")

    }

    fun generateDummyRequestBody(): RequestBody {
        val dummyText = "text"
        return dummyText.toRequestBody()
    }

    fun generateDummyMultipartFile(): MultipartBody.Part {
        val dummyText = "text"
        return MultipartBody.Part.create(dummyText.toRequestBody())
    }





}