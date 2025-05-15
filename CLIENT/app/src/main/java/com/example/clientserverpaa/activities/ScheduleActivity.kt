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
import com.google.android.material.bottomnavigation.BottomNavigationView

class ScheduleActivity : AppCompatActivity() {

    private lateinit var recyclerViewSchedule: RecyclerView
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.schedule)

        recyclerViewSchedule = findViewById(R.id.recyclerViewSchedule)
        recyclerViewSchedule.layoutManager = LinearLayoutManager(this)

        val scheduleList = listOf(
            ScheduleItem("Android Development", "2025-10-15"),
            ScheduleItem("Web Development", "2025-10-16"),
            ScheduleItem("Data Science", "2025-10-17"),
            ScheduleItem("Machine Learning", "2025-10-18"),
            ScheduleItem("UI/UX Design", "2025-10-19")
        )

        val scheduleAdapter = ScheduleAdapter(scheduleList)
        recyclerViewSchedule.adapter = scheduleAdapter

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
                R.id.navigation_schedule -> {
                    true
                }
                else -> false
            }
        }
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

        override fun getItemCount(): Int {
            return scheduleList.size
        }

        inner class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textViewCourseName: TextView = itemView.findViewById(R.id.textViewCourseName)
            val textViewCourseDate: TextView = itemView.findViewById(R.id.textViewCourseDate)
        }
    }

    private data class ScheduleItem(val courseName: String, val courseDate: String)
}