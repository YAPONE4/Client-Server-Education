package com.example.clientserverpaa.utilities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clientserverpaa.R

class CourseAdapter(
    private var courseList: List<Course>,
    private val onItemClick: (Course) -> Unit
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    private var fullCourseList: List<Course> = courseList // Сохраняем оригинальный список
    private var filteredCourses: List<Course> = fullCourseList // Список с фильтром

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = filteredCourses[position] // Используем filteredCourses для отображения
        holder.textViewCourseName.text = course.title
        holder.textViewCourseDescription.text = course.body

        holder.itemView.setOnClickListener {
            onItemClick(course)
        }
    }

    override fun getItemCount(): Int {
        return filteredCourses.size // Возвращаем размер фильтрованного списка
    }

    fun updateData(newCourseList: List<Course>) {
        fullCourseList = newCourseList // Обновляем оригинальный список
        filteredCourses = newCourseList // Обновляем фильтрованный список
        courseList = newCourseList // Обновляем текущий список
        notifyDataSetChanged()
    }

    fun filter(query: String?) {
        filteredCourses = if (query.isNullOrEmpty()) {
            fullCourseList // Если нет запроса, показываем все
        } else {
            fullCourseList.filter { it.title.contains(query, ignoreCase = true) } // Фильтруем по заголовку
        }
        notifyDataSetChanged()
    }

    fun clearFilter() {
        filteredCourses = fullCourseList // Сбрасываем фильтр
        notifyDataSetChanged()
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewCourseName: TextView = itemView.findViewById(R.id.textViewCourseName)
        val textViewCourseDescription: TextView = itemView.findViewById(R.id.textViewCourseDescription)
    }
}