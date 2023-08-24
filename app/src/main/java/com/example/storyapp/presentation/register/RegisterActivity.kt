@file:Suppress("DEPRECATION")

package com.example.storyapp.presentation.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityRegisterBinding
import com.example.storyapp.presentation.Locator.Locator
import com.example.storyapp.presentation.login.LoginActivity
import com.example.storyapp.presentation.story.StoryActivity
import com.example.storyapp.presentation.utils.ResultState
import com.example.storyapp.presentation.utils.showDialogError
import com.example.storyapp.presentation.utils.showDialogLoading
import com.example.storyapp.presentation.utils.showDialogSuccess

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels {
        Locator.registerViewModelFactory
    }
    private lateinit var loadingDialog: AlertDialog
    private lateinit var successDialog: AlertDialog
    private lateinit var errorDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingDialog = showDialogLoading(this)
        successDialog = showDialogSuccess(this, "Registration successful")
        errorDialog = showDialogError(this, "Registration failed")

        binding.btnRegister.setOnClickListener {
            val name = binding.etNameRegister.text.toString()
            val email = binding.etEmailRegister.text.toString()
            val password = binding.etPasswordRegister.text.toString()
            val confirmPassword = binding.etConfirmPasswordRegister.text.toString()

            if (password == confirmPassword) {
                viewModel.registerUser(name, email, password)
            } else {
                Toast.makeText(this, "Password and confirm password do not match", Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.registerViewState.collect { viewState ->
                if (viewState.isLoading) {
                    loadingDialog.show()
                } else {
                    loadingDialog.dismiss()

                    when (viewState.resultRegisterUser) {
                        is ResultState.Success -> {
                            successDialog.show()

                            // Navigate to MainActivity after the success dialog is dismissed
                            successDialog.setOnDismissListener {
                                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                        is ResultState.Error -> errorDialog.show()
                        else -> {
                        }
                    }

                }
            }
        }
    }


}

