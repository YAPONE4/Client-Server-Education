package com.example.clientserverpaa.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clientserverpaa.R
import com.example.clientserverpaa.net.AuthApiClient
import com.example.clientserverpaa.utilities.Course
import com.example.clientserverpaa.utilities.ScheduleItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class ScheduleActivity : AppCompatActivity() {

    private lateinit var recyclerViewSchedule: RecyclerView
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.schedule)

        recyclerViewSchedule = findViewById(R.id.recyclerViewSchedule)
        recyclerViewSchedule.layoutManager = LinearLayoutManager(this)

        val sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
        val userId = sharedPreferences.getInt("userId", -1)

        if (userId != -1) {
            loadSchedule(userId)
        } else {
            Toast.makeText(this, "Ошибка: пользователь не авторизован", Toast.LENGTH_SHORT).show()
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.navigation_schedule
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_search -> {
                    startActivity(Intent(this, SearchActivity::class.java))
                    true
                }
                R.id.navigation_my_courses -> {
                    startActivity(Intent(this, MyCoursesActivity::class.java))
                    true
                }
                R.id.navigation_schedule -> true
                else -> false
            }
        }
    }

    private fun loadSchedule(userId: Int) {
        AuthApiClient.api.getUserSchedule(userId).enqueue(object : Callback<List<ScheduleItem>> {
            override fun onResponse(
                call: Call<List<ScheduleItem>>,
                response: Response<List<ScheduleItem>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val scheduleList = response.body()!!
                    recyclerViewSchedule.adapter = ScheduleAdapter(scheduleList)
                } else {
                    Toast.makeText(this@ScheduleActivity, "Ошибка загрузки расписания", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ScheduleItem>>, t: Throwable) {
                Toast.makeText(this@ScheduleActivity, "Ошибка: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private inner class ScheduleAdapter(private val scheduleList: List<ScheduleItem>) :
        RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
            val view = layoutInflater.inflate(R.layout.item_schedule, parent, false)
            return ScheduleViewHolder(view)
        }

        override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
            val scheduleItem = scheduleList[position]
            holder.textViewCourseName.text = scheduleItem.courseName
            holder.textViewCourseDate.text = scheduleItem.courseDate

            holder.itemView.setOnClickListener {
                Toast.makeText(it.context, "Clicked: ${scheduleItem.courseName}", Toast.LENGTH_SHORT).show()
            }
        }

        override fun getItemCount(): Int = scheduleList.size

        inner class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textViewCourseName: TextView = itemView.findViewById(R.id.textViewCourseName)
            val textViewCourseDate: TextView = itemView.findViewById(R.id.textViewCourseDate)
        }
    }
}