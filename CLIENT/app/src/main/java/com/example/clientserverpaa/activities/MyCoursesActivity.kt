package com.example.clientserverpaa.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clientserverpaa.R
import com.example.clientserverpaa.net.AuthApiClient
import com.example.clientserverpaa.utilities.Course
import com.example.clientserverpaa.utilities.CourseAdapter
import com.example.clientserverpaa.utilities.CourseListResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyCoursesActivity : AppCompatActivity() {

    private lateinit var recyclerViewCourses: RecyclerView
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_courses)

        recyclerViewCourses = findViewById(R.id.recyclerViewCourses)
        recyclerViewCourses.layoutManager = LinearLayoutManager(this)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.navigation_my_courses
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_search -> {
                    startActivity(Intent(this, SearchActivity::class.java))
                    true
                }
                R.id.navigation_my_courses -> true
                R.id.navigation_schedule -> {
                    startActivity(Intent(this, ScheduleActivity::class.java))
                    true
                }
                else -> false
            }
        }

        val sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("userId", -1)

        if (userId != -1) {
            loadUserCourses(userId)
        } else {
            Toast.makeText(this, "Ошибка: пользователь не авторизован", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadUserCourses(userId: Int) {
        val userId = getSharedPreferences("settings", MODE_PRIVATE).getInt("userId", -1)
        if (userId != -1) {
            AuthApiClient.api.getUserCourses(userId).enqueue(object : Callback<CourseListResponse> {
                override fun onResponse(
                    call: Call<CourseListResponse>,
                    response: Response<CourseListResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val courses = response.body()!!.courses
                        val adapter = CourseAdapter(courses) { course ->
                            val intent = Intent(this@MyCoursesActivity, CourseActivity::class.java).apply {
                                putExtra("courseName", course.title)
                                putExtra("courseDescription", course.description)
                            }
                            startActivity(intent)
                        }
                        recyclerViewCourses.adapter = adapter
                    } else {
                        Toast.makeText(this@MyCoursesActivity, "Ошибка получения курсов", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<CourseListResponse>, t: Throwable) {
                    Toast.makeText(this@MyCoursesActivity, "Ошибка загрузки курсов: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}