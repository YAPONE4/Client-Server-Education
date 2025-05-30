package com.example.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object Tokens : IntIdTable("tokens") {
    val email = varchar("username", 50).uniqueIndex()
    val token = varchar("token", 64)

    fun insertToken(mail: String, tokenStr: String) {
        deleteWhere { email eq mail }
        insert {
            it[email] = mail
            it[token] = tokenStr
        }
    }
}