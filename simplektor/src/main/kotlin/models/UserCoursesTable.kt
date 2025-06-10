package com.example.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*

object UserCourses : IntIdTable() {
    val user = reference("user_id", Users)
    val course = reference("course_id", Courses)

    fun insertUserCourse(userIdVal: Int, courseIdVal: Int) {
        insert {
            it[user] = userIdVal
            it[course] = courseIdVal
        }
    }
}