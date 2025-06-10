package com.example.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.*

object Courses : IntIdTable() {
    val title = varchar("title", 255)
    val description = text("description")
    val date = varchar("date", 255)

    fun insertCourse(titleVal: String, descriptionVal: String, dateVal: String) {
        insert {
            it[title] = titleVal
            it[description] = descriptionVal
            it[date] = dateVal
        }
    }
}