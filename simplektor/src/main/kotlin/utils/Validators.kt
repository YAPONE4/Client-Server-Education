package com.example.utils

private val emailRegex =
    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

fun isEmailValid(email: String) = emailRegex.matches(email)