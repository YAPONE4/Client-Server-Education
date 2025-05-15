package com.example.clientserverpaa.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clientserverpaa.utilities.Course
import com.example.clientserverpaa.utilities.CourseAdapter
import com.example.clientserverpaa.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MyCoursesActivity : AppCompatActivity() {

    private lateinit var recyclerViewCourses: RecyclerView
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_courses)

        recyclerViewCourses = findViewById(R.id.recyclerViewCourses)
        recyclerViewCourses.layoutManager = LinearLayoutManager(this)

        val courseList = listOf(
            Course(1, "Android Development", "Description"),
            Course(2, "Web Development", "Description"),
            Course(3, "Data Science", "Description")
        )

        val courseAdapter = CourseAdapter(courseList) { course ->
            val intent = Intent(this, CourseActivity::class.java).apply {
                putExtra("courseName", course.title)
                putExtra("courseDescription", course.body)
            }
            startActivity(intent)
        }

        recyclerViewCourses.adapter = courseAdapter

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.navigation_my_courses
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_search -> {
                    startActivity(Intent(this, SearchActivity::class.java))
                    true
                }
                R.id.navigation_my_courses -> {
                    true
                }
                R.id.navigation_schedule -> {
                    startActivity(Intent(this, ScheduleActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}