package com.example.storyapp.fake

import com.example.storyapp.presentation.domain.entity.UserEntity
import com.example.storyapp.presentation.domain.interfaces.UserPreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUserPreferenceRepository : UserPreferenceRepository {

    private var userEntity: UserEntity? = null

    override val userData: Flow<UserEntity>
        get() = flow {
            userEntity?.let { emit(it) }
        }

    override suspend fun saveUser(userEntity: UserEntity) {
        this.userEntity = userEntity
    }

    override suspend fun clearUser() {
        userEntity = null
    }
}