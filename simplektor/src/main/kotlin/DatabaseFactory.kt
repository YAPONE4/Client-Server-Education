package com.example


import com.example.models.Courses
import com.example.models.Users
import com.example.models.Tokens
import com.example.models.UserCourses
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/csdb",
            driver = "org.postgresql.Driver",
            user   = "postgres",
            password = "postgres"
        )
        transaction {
            SchemaUtils.createMissingTablesAndColumns(Users, Tokens, Courses, UserCourses)
        }
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}