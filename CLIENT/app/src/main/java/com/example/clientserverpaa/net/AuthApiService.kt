package com.example.clientserverpaa.net

import com.example.clientserverpaa.utilities.Course
import com.example.clientserverpaa.utilities.CourseListResponse
import com.example.clientserverpaa.utilities.DBCourse
import com.example.clientserverpaa.utilities.LoginRequest
import com.example.clientserverpaa.utilities.RegisterRequest
import com.example.clientserverpaa.utilities.ScheduleItem
import com.example.clientserverpaa.utilities.TokenResponse
import com.example.clientserverpaa.utilities.UserCourseRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApiService {
    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<Unit>

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<TokenResponse>

    @GET("courses")
    fun getCourses(): Call<CourseListResponse>

    @GET("user-courses/{userId}")
    fun getUserCourses(@Path("userId") userId: Int): Call<CourseListResponse>

    @GET("user-schedule/{userId}")
    fun getUserSchedule(@Path("userId") userId: Int): Call<List<ScheduleItem>>

    @POST("user-courses")
    fun subscribeToCourse(@Body userCourseRequest: UserCourseRequest): Call<Void>
}