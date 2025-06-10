package com.example.routing

import com.example.controllers.CourseController
import com.example.login.LoginController
import com.example.models.*
import com.example.register.RegisterController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureRouting() {

    routing {

        get("/test") { call.respond(MessageResponse("OK")) }

        post("/register") {
            val receive = call.receive<RegisterReceiveRemote>()
            val result  = RegisterController().registerNewUser(receive)

            result.fold(
                onSuccess = { call.respond(HttpStatusCode.Created, it) },
                onFailure = { call.respond(HttpStatusCode.BadRequest, MessageResponse(it.message ?: "")) }
            )
        }

        post("/login") {
            val receive = call.receive<LoginReceiveRemote>()
            val result  = LoginController().performLogin(receive)

            result.fold(
                onSuccess = { call.respond(HttpStatusCode.OK, it) },
                onFailure = { call.respond(HttpStatusCode.BadRequest, MessageResponse(it.message ?: "")) }
            )
        }
        get("/courses") {
            val courses = CourseController().getAllCourses()
            call.respond(HttpStatusCode.OK, CourseListResponse(courses))
        }

        post("/user-courses") {
            val request = call.receive<UserCourseRequest>()

            try {
                val result = transaction {
                    val alreadyExists = (UserCourses innerJoin Courses).select {
                        (UserCourses.user eq request.userId) and (UserCourses.course eq request.courseId)
                    }.any()

                    if (alreadyExists) {
                        return@transaction "conflict"
                    }

                    UserCourses.insert {
                        it[user] = request.userId
                        it[course] = request.courseId
                    }

                    return@transaction "created"
                }

                when (result) {
                    "conflict" -> call.respond(HttpStatusCode.Conflict, "Вы уже записаны на этот курс")
                    "created" -> call.respond(HttpStatusCode.Created, "Запись на курс прошла успешно")
                    else -> call.respond(HttpStatusCode.InternalServerError, "Неизвестный результат операции")
                }

            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Ошибка сервера: ${e.message}")
            }
        }

        get("/user-courses/{userId}") {
            val userId = call.parameters["userId"]?.toIntOrNull()
            if (userId == null) {
                call.respond(HttpStatusCode.BadRequest, MessageResponse("Invalid user ID"))
                return@get
            }
            val courses = CourseController().getUserCourses(userId)
            call.respond(HttpStatusCode.OK, CourseListResponse(courses))
        }

        get("/user-schedule/{userId}") {
            val userId = call.parameters["userId"]?.toIntOrNull()
            if (userId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid user ID")
                return@get
            }

            try {
                val scheduleItems = transaction {
                    (UserCourses innerJoin Courses).select {
                        UserCourses.user eq userId
                    }.map {
                        ScheduleItemResponse(
                            courseName = it[Courses.title],
                            courseDate = it[Courses.date]
                        )
                    }
                }

                call.respond(scheduleItems)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error: ${e.message}")
            }
        }

    }
}