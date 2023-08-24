package com.example.storyapp.presentation.data.source.Api

import com.example.storyapp.presentation.data.responses.GeneralResponse
import com.example.storyapp.presentation.data.responses.LoginResponse
import com.example.storyapp.presentation.data.responses.StoryDetailResponse
import com.example.storyapp.presentation.data.responses.StoryListResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiServices {
    @POST("register")
    suspend fun register(
        @Body requestBody: HashMap<String, String>
    ): GeneralResponse

    @POST("login")
    suspend fun login(
        @Body requestBody: HashMap<String, String>
    ): LoginResponse

    @GET("stories")
    suspend fun stories(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): StoryListResponse

    @GET("stories/{id}")
    suspend fun storyDetail(
        @Path("id") id: String
    ): StoryDetailResponse

    @Multipart
    @POST("stories")
    suspend fun addStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: Float?,
        @Part("lon") lon: Float?,
    ): GeneralResponse

    @GET("stories")
    suspend fun storiesLocation(
        @Query("location") id: Int
    ): StoryListResponse
}
