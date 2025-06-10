package com.example.clientserverpaa.utilities

data class RegisterRequest(
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val userId: Int
)

data class ErrorResponse(
    val error: String
)

data class CourseListResponse(
    val courses: List<Course>
)

data class TokenResponse(
    val token: String,
    val userId: Int
)

data class ScheduleItem(
    val courseName: String,
    val courseDate: String
)

data class UserCourseRequest(
    val userId: Int,
    val courseId: Int
)