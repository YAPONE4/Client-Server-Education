package com.example.clientserverpaa.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.edit
import com.example.clientserverpaa.R
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.clientserverpaa.net.AuthApiClient
import com.example.clientserverpaa.net.AuthApiService
import com.example.clientserverpaa.utilities.LoginRequest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var themeSwitch: SwitchCompat
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val isNightMode = prefs.getBoolean("night_mode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isNightMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.authorization)

        themeSwitch = findViewById(R.id.themeSwitch)
        themeSwitch.isChecked = isNightMode

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit {
                putBoolean("night_mode", isChecked)
            }
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
            recreate()
        }

        val buttonSignIn = findViewById<Button>(R.id.buttonSignIn)
        val buttonSignUp = findViewById<Button>(R.id.buttonSignUp)

        val emailField = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val passwordField = findViewById<EditText>(R.id.editTextPassword)

        buttonSignIn.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            lifecycleScope.launch {
                try {
                    val response = AuthApiClient.api.login(LoginRequest(email, password))
                    if (response.isSuccessful) {
                        Toast.makeText(this@MainActivity, "Успешный вход", Toast.LENGTH_SHORT).show()
                        val token = response.body()?.token
                        if (token != null) {
                            prefs.edit {
                                putString("auth_token", token)
                            }
                            startActivity(Intent(this@MainActivity, MyCoursesActivity::class.java))
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Неверный email или пароль", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "Ошибка сервера", Toast.LENGTH_SHORT).show()
                }
            }
        }

        buttonSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}