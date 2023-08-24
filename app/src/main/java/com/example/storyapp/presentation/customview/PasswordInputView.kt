package com.example.storyapp.presentation.customview

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import com.example.storyapp.R
import com.google.android.material.textfield.TextInputLayout

class PasswordInputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextInputLayout(context, attrs, defStyleAttr) {

    private val passwordEditText: EditText

    init {
        passwordEditText = EditText(context).apply {
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            hint = context.getString(R.string.password)
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable) {
                    if (s.length < 8) {
                        error = "Password must be at least 8 characters long"
                    } else {
                        error = null
                    }
                }
            })
        }
        addView(passwordEditText)
    }

    fun validate(): Boolean {
        val password = editText?.text.toString()

        if (password.isEmpty()) {
            error = "Password cannot be empty"
            return false
        } else if (password.length < 8) {
            error = "Password must be at least 8 characters long"
            return false
        }

        error = null
        return true
    }

    fun getPasswordText(): String {
        return editText?.text.toString()
    }
}
