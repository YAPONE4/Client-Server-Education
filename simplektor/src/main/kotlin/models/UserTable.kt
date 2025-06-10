package com.example.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*

object Users : IntIdTable() {
    val email = varchar("username", 50).uniqueIndex()
    val passwordHash = varchar("password_hash", 64)

    fun fetchUser(mail: String) = select { email eq mail }
        .singleOrNull()
        ?.let { UserDTO(it[id].value,it[email], it[passwordHash]) }
}