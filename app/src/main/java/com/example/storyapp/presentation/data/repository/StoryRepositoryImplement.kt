package com.example.storyapp.presentation.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.storyapp.presentation.data.paging.StoryRemoteMediator
import com.example.storyapp.presentation.data.responses.StoryResponse
import com.example.storyapp.presentation.data.source.Api.ApiServices
import com.example.storyapp.presentation.data.source.database.StoryDatabase
import com.example.storyapp.presentation.domain.interfaces.StoryRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class StoryRepositoryImplement (
    private val storyDatabase: StoryDatabase, private val api: ApiServices
) : StoryRepository {


    override fun getStories(): Flow<PagingData<StoryResponse>> {
         @OptIn(ExperimentalPagingApi::class) return Pager(config = PagingConfig(
             pageSize = 5
         ), remoteMediator = StoryRemoteMediator(storyDatabase, api), pagingSourceFactory = {
             storyDatabase.storyDao().getAllStories()
         }).flow
     }

    override fun getStory(id: String) = flow {
        try {
            val story = api.storyDetail(id)
            println("Received story data from API: $story")
            emit(story)
        } catch (e: Exception) {
            println("Failed to fetch story data: ${e.localizedMessage}")
        }
    }.flowOn(Dispatchers.IO)


    override fun addStory(file: File, description: String, latLng: LatLng?) = flow {
        val requestBody = MultipartBody.Part.createFormData(
            "photo", file.name, file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        )
        val desc = description.toRequestBody("text/plain".toMediaType())
        val lat = latLng?.latitude?.toFloat()
        val lng = latLng?.longitude?.toFloat()

        emit(
            api.addStory(requestBody, desc, lat, lng)
        )
    }.flowOn(Dispatchers.IO)



    override fun getStoriesLocation(id: Int) = flow {
        emit(
            api.storiesLocation(id)
        )
    }.flowOn(Dispatchers.IO)
}