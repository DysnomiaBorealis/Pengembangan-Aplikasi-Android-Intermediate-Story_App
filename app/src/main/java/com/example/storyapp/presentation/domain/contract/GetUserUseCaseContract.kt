package com.example.storyapp.presentation.domain.contract

import com.example.storyapp.presentation.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow


interface GetUserUseCaseContract {
    operator fun invoke(): Flow<UserEntity>
}