package com.example.storyapp.presentation.splash


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.R
import com.example.storyapp.presentation.landing.LandingActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        delayAndGoToLogin()
    }

    private fun delayAndGoToLogin() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LandingActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

    }
}