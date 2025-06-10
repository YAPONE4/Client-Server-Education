package com.example.clientserverpaa.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clientserverpaa.R
import com.example.clientserverpaa.net.AuthApiClient
import com.example.clientserverpaa.utilities.UserCourseRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.course)

        val textViewCourseName: TextView = findViewById(R.id.textViewCourseName)
        val textViewCourseAuthor: TextView = findViewById(R.id.textViewCourseAuthor)
        val textViewCourseDifficulty: TextView = findViewById(R.id.textViewCourseDifficulty)
        val textViewCourseDescription: TextView = findViewById(R.id.textViewCourseDescription)
        val buttonSubscribe: Button = findViewById(R.id.buttonSubscribe)

        val courseName = intent.getStringExtra("courseName") ?: "Course Name"
        val courseAuthor = intent.getStringExtra("courseAuthor") ?: "Author Name"
        val courseDifficulty = intent.getStringExtra("courseDifficulty") ?: "Intermediate"
        val courseDescription = intent.getStringExtra("courseDescription") ?: "This is a course description."

        textViewCourseName.text = courseName
        textViewCourseAuthor.text = courseAuthor
        textViewCourseDifficulty.text = courseDifficulty
        textViewCourseDescription.text = courseDescription

        val buttonBack = findViewById<ImageButton>(R.id.backButton)
        buttonBack.setOnClickListener {
            startActivity(Intent(this, MyCoursesActivity::class.java))
        }

        buttonSubscribe.setOnClickListener {
            val sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE)
            val userId = sharedPreferences.getInt("userId", -1)
            val courseId = intent.getIntExtra("courseId", -1)

            if (userId == -1 || courseId == -1) {
                Toast.makeText(this, "Ошибка: пользователь или курс не определён", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = UserCourseRequest(userId, courseId)
            AuthApiClient.api.subscribeToCourse(request).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@CourseActivity, "Вы успешно подписаны на курс", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@CourseActivity, "Ошибка подписки: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@CourseActivity, "Ошибка сети: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}