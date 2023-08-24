package com.example.storyapp.presentation.domain.contract

import com.example.storyapp.presentation.utils.ResultState
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import java.io.File

interface AddStoryUseCaseContract {
    operator fun invoke(file: File, description: String, latLng: LatLng?): Flow<ResultState<String>>
}