package com.example.storyapp.application

import android.app.Application
import com.example.storyapp.presentation.Locator.Locator

class StoryApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize the Locator with this application
        Locator.initWith(this)
    }
}
