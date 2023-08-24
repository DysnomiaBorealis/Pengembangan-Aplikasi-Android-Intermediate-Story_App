package com.example.storyapp.presentation.domain.interfaces

import androidx.paging.PagingData
import com.google.android.gms.maps.model.LatLng
import com.example.storyapp.presentation.data.responses.GeneralResponse
import com.example.storyapp.presentation.data.responses.StoryDetailResponse
import com.example.storyapp.presentation.data.responses.StoryListResponse
import com.example.storyapp.presentation.data.responses.StoryResponse
import kotlinx.coroutines.flow.Flow
import java.io.File


interface StoryRepository {
    fun getStories(): Flow<PagingData<StoryResponse>>
    fun getStory(id: String): Flow<StoryDetailResponse>
    fun addStory(file: File, description: String, latLng: LatLng?): Flow<GeneralResponse>
    fun getStoriesLocation(id: Int): Flow<StoryListResponse>
}