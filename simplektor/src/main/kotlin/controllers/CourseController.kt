package com.example.controllers

import com.example.DatabaseFactory.dbQuery
import com.example.models.CourseDTO
import com.example.models.Courses
import com.example.models.UserCourses
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.innerJoin
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.time.format.DateTimeFormatter

class CourseController {

    suspend fun getAllCourses(): List<CourseDTO> = dbQuery {
        Courses.selectAll().map {
            CourseDTO(
                id = it[Courses.id].value,
                title = it[Courses.title],
                description = it[Courses.description],
                date = it[Courses.date]
            )
        }
    }
    suspend fun getUserCourses(userId: Int): List<CourseDTO> = dbQuery {
        (UserCourses innerJoin Courses)
            .select { UserCourses.user eq userId }
            .map {
                CourseDTO(
                    id = it[Courses.id].value,
                    title = it[Courses.title],
                    description = it[Courses.description],
                    date = it[Courses.date]
                )
            }
    }
}