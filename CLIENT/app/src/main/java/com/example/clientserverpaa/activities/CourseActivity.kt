package com.example.clientserverpaa.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clientserverpaa.R

class CourseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.course)

        // Инициализация элементов UI
        val textViewCourseName: TextView = findViewById(R.id.textViewCourseName)
        val textViewCourseAuthor: TextView = findViewById(R.id.textViewCourseAuthor)
        val textViewCourseDifficulty: TextView = findViewById(R.id.textViewCourseDifficulty)
        val textViewCourseDescription: TextView = findViewById(R.id.textViewCourseDescription)
        val buttonSubscribe: Button = findViewById(R.id.buttonSubscribe)

        // Получаем данные о курсе из Intent
        val courseName = intent.getStringExtra("courseName") ?: "Course Name"
        val courseAuthor = intent.getStringExtra("courseAuthor") ?: "Author Name"
        val courseDifficulty = intent.getStringExtra("courseDifficulty") ?: "Intermediate"
        val courseDescription = intent.getStringExtra("courseDescription") ?: "This is a course description."

        // Устанавливаем данные в UI
        textViewCourseName.text = courseName
        textViewCourseAuthor.text = courseAuthor
        textViewCourseDifficulty.text = courseDifficulty
        textViewCourseDescription.text = courseDescription

        val buttonBack = findViewById<ImageButton>(R.id.backButton)
        buttonBack.setOnClickListener {
            startActivity(Intent(this, MyCoursesActivity::class.java))
        }

        buttonSubscribe.setOnClickListener {
            // TODO: Реализовать логику подписки
            Toast.makeText(this, "Subscribed to $courseName", Toast.LENGTH_SHORT).show()
        }
    }
}