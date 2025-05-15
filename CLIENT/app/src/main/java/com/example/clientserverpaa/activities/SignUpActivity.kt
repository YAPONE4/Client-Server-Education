package com.example.clientserverpaa.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.clientserverpaa.R

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)

        val buttonBack = findViewById<ImageButton>(R.id.backButton)
        val buttonSignUp = findViewById<Button>(R.id.buttonSignUp)

        buttonBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        buttonSignUp.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}