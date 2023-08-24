package com.example.storyapp.presentation.Locator

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.storyapp.presentation.addstory.AddStoryViewModel
import com.example.storyapp.presentation.data.repository.AuthRepositoryImplement
import com.example.storyapp.presentation.data.repository.StoryRepositoryImplement
import com.example.storyapp.presentation.data.source.local.UserPreferenceImpl
import com.example.storyapp.presentation.data.source.Api.RetrofitBuild
import com.example.storyapp.presentation.data.source.database.StoryDatabase
import com.example.storyapp.presentation.domain.usecase.*
import com.example.storyapp.presentation.login.LoginViewModel
import com.example.storyapp.presentation.map.MapsViewModel
import com.example.storyapp.presentation.register.RegisterViewModel
import com.example.storyapp.presentation.story.StoryViewModel
import com.example.storyapp.presentation.storydetail.StoryDetailViewModel

object Locator {
    private var application: Application? = null

    private inline val requireApplication
        get() = application ?: error("Missing call: initWith(application)")

    fun initWith(application: Application) {
        this.application = application
    }

    // Data Store
    private val Context.dataStore by preferencesDataStore(name = "user_preferences")

    // ViewModel Factory
    val loginViewModelFactory
        get() = LoginViewModel.Factory(
            loginUseCase = loginUseCase
        )
    val registerViewModelFactory
        get() = RegisterViewModel.Factory(
            registerUseCase = registerUseCase
        )
    val storyViewModelFactory
        get() = StoryViewModel.Factory(
            getStoriesUseCase = getStoriesUseCase,
            getUserUseCase = getUserUseCase,
            logoutUseCase = logoutUseCase,
            userPreferenceRepository = userPreferencesRepository
        )

    val storyDetailViewModelFactory
        get() = StoryDetailViewModel.Factory(
            getStoryDetailUseCase = getStoryDetailUseCase
        )
    val addStoryViewModelFactory
        get() = AddStoryViewModel.Factory(
            addStoryUseCase = addStoryUseCase
        )

    val mapsViewModelFactory
        get() = MapsViewModel.Factory(
            getStoriesLocationUseCase = getStoriesLocationUseCase
        )


    // UseCases Injection
    private val loginUseCase get() = LoginUseCase(userPreferencesRepository, authRepository)
    private val registerUseCase get() = RegisterUseCase(authRepository)
    private val getUserUseCase get() = GetUserUseCase(userPreferencesRepository)
    private val getStoriesUseCase get() = GetStoriesUseCase(storyRepository)
    private val logoutUseCase get() = LogoutUseCase(userPreferencesRepository)
    private val getStoryDetailUseCase get() = GetStoryDetailUseCase(storyRepository)
    private val addStoryUseCase get() = AddStoryUseCase(storyRepository)
    private val getStoriesLocationUseCase get() = GetStoriesLocationUseCase(storyRepository)

    // Repository Injection
    val userPreferencesRepository by lazy {
        UserPreferenceImpl(requireApplication.dataStore)
    }
    private val authRepository by lazy {
        AuthRepositoryImplement(
            api = RetrofitBuild(requireApplication.dataStore).apiService,
            userPreferenceRepository = userPreferencesRepository
        )
    }

    private val storyRepository by lazy {
        StoryRepositoryImplement(
            StoryDatabase.getDatabase(requireApplication),
            RetrofitBuild(requireApplication.dataStore).apiService
        )
    }
}
