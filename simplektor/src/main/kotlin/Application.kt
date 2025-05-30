package com.example.simplektor

import com.example.DatabaseFactory
import com.example.routing.configureRouting
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.*
import org.slf4j.event.Level
import kotlinx.serialization.json.Json

fun main() {
    // запускаем БД + плагины, а потом сам сервер
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        DatabaseFactory.init()          // <-- подключаемся к PostgreSQL
        configureContentNegotiation()   // json()
        configureRouting()              // /register, /login, /test
    }.start(wait = true)
}

fun Application.configureContentNegotiation() {
    install(ContentNegotiation) {
        json(Json { prettyPrint = true; ignoreUnknownKeys = true })
    }
}

