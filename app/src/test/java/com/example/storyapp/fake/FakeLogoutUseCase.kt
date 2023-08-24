package com.example.storyapp.fake

import com.example.storyapp.presentation.domain.contract.LogoutUseCaseContract

class FakeLogoutUseCase : LogoutUseCaseContract {


    override suspend fun invoke() = Unit


}