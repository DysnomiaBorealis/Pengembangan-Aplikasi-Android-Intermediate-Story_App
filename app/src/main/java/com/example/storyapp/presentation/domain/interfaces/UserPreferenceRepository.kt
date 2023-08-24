package com.example.storyapp.presentation.domain.interfaces

import com.example.storyapp.presentation.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {
    val userData: Flow<UserEntity>
    suspend fun saveUser(userEntity: UserEntity)
    suspend fun clearUser()
}