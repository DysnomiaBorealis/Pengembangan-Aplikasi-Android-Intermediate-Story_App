package com.example.storyapp.presentation.landing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.storyapp.databinding.ActivityLandingBinding
import com.example.storyapp.presentation.Locator.Locator
import com.example.storyapp.presentation.login.LoginActivity
import com.example.storyapp.presentation.story.StoryActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LandingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandingBinding
    private val userPreference by lazy { Locator.userPreferencesRepository }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkIfUserIsLoggedIn()
        onAction()
    }

    private fun onAction() {
        binding.btnLanding.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkIfUserIsLoggedIn() {
        lifecycleScope.launch {
            userPreference.userData.first().let { user ->
                if (user.id.isNotEmpty() && user.token.isNotEmpty()) {
                    // User is logged in, navigate to StoryActivity
                    val intent = Intent(this@LandingActivity, StoryActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
