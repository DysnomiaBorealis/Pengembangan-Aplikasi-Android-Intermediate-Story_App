package com.example.storyapp.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityLoginBinding
import com.example.storyapp.presentation.Locator.Locator
import com.example.storyapp.presentation.register.RegisterActivity
import com.example.storyapp.presentation.story.StoryActivity
import com.example.storyapp.presentation.utils.*

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val loadingDialog: AlertDialog by lazy { showDialogLoading(this) }
    private val successDialog: AlertDialog by lazy { showDialogSuccess(this, "Success message") }
    private val viewModel by viewModels<LoginViewModel>(factoryProducer = { Locator.loginViewModelFactory })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val context = this

        viewModel.loginState.launchAndCollectIn(this) { state ->
            when (state.resultVerifyUser) {
                is ResultState.Success<String> -> {
                    loadingDialog.dismiss()
                    successDialog.show() // This line shows the success dialog
                    startActivity(
                        Intent(
                            this@LoginActivity, StoryActivity::class.java
                        )
                    )
                    finish()
                }

                is ResultState.Loading<*> -> loadingDialog.show()
                is ResultState.Error<*> -> {
                    loadingDialog.dismiss()
                    showDialogError(context, state.resultVerifyUser.message)
                }

                else -> Unit
            }
        }

        binding.btnLogin.setOnClickListener {
            if (binding.etEmailLogin.error == null && binding.etPasswordLogin.validate()) {
                viewModel.doLogin(
                    email = binding.etEmailLogin.text.toString(),
                    password = binding.etPasswordLogin.getPasswordText()
                )
            } else {
                Toast.makeText(this, getString(R.string.input_invalid), Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRegister.setOnClickListener {
            startActivity(
                Intent(
                    this, RegisterActivity::class.java
                )
            )
        }
    }
}
