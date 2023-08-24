package com.example.storyapp.presentation.domain.interfaces

import com.example.storyapp.presentation.data.responses.GeneralResponse
import com.example.storyapp.presentation.data.responses.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun register(email: String, password: String, name: String): GeneralResponse
    fun login(email: String, password: String): Flow<LoginResponse>
}