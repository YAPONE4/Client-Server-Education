package com.example.models

@kotlinx.serialization.Serializable
data class UserDTO(val id: Int, val email: String, val passwordHash: String)

@kotlinx.serialization.Serializable
data class RegisterReceiveRemote(val email: String, val password: String)

@kotlinx.serialization.Serializable
data class LoginReceiveRemote(val email: String, val password: String)

@kotlinx.serialization.Serializable
data class TokenResponseRemote(
    val token: String,
    val userId: Int
)

@kotlinx.serialization.Serializable
data class MessageResponse(val message: String)
@kotlinx.serialization.Serializable
data class CourseDTO(
    val id: Int,
    val title: String,
    val description: String,
    val date: String
)

@kotlinx.serialization.Serializable
data class CourseListResponse(
    val courses: List<CourseDTO>
)
@kotlinx.serialization.Serializable
data class ScheduleItemResponse(
    val courseName: String,
    val courseDate: String
)
@kotlinx.serialization.Serializable
data class UserCourseRequest(
    val userId: Int,
    val courseId: Int
)