package com.example.storyapp.fake

import com.example.storyapp.presentation.domain.contract.GetUserUseCaseContract
import com.example.storyapp.presentation.domain.entity.UserEntity
import com.example.storyapp.utils.FakeFlowDelegate
import kotlinx.coroutines.flow.Flow


class FakeGetUserUseCase : GetUserUseCaseContract {

    val fakeDelegate = FakeFlowDelegate<UserEntity>()

    override fun invoke(): Flow<UserEntity> = fakeDelegate.flow

}