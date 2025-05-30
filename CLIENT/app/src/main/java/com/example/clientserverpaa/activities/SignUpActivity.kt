package com.example.clientserverpaa.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.clientserverpaa.R
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.clientserverpaa.net.AuthApiClient
import com.example.clientserverpaa.utilities.RegisterRequest
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)

        val buttonBack = findViewById<ImageButton>(R.id.backButton)
        val buttonSignUp = findViewById<Button>(R.id.buttonSignUp)

        val emailField = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val passwordField = findViewById<EditText>(R.id.editTextPassword)
        val passwordVerify = findViewById<EditText>(R.id.editTextRepeatPassword)

        buttonBack.setOnClickListener {
            finish()
        }

        buttonSignUp.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()
            val passwordRepeated = passwordVerify.text.toString()

            if (passwordRepeated == password) {

                lifecycleScope.launch {
                    try {
                        val response = AuthApiClient.api.register(RegisterRequest(email, password))
                        if (response.isSuccessful) {
                            Toast.makeText(
                                this@SignUpActivity,
                                "Успешно зарегистрированы",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                        } else {
                            Toast.makeText(
                                this@SignUpActivity,
                                "Ошибка регистрации",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this@SignUpActivity, "Сервер недоступен", Toast.LENGTH_SHORT)
                            .show()
                        Log.e("REGISTER", "Сетевая ошибка: ${e.localizedMessage}", e)
                    }
                }
            } else {
                Toast.makeText(
                    this@SignUpActivity,
                    "Пароли не совпадают",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}