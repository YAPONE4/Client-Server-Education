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
            prefs.edit() {
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

        buttonSignIn.setOnClickListener {
            startActivity(Intent(this, MyCoursesActivity::class.java))
        }
        buttonSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}
