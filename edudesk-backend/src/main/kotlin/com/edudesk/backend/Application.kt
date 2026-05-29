package com.edudesk.backend

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import io.ktor.server.routing.*
import io.ktor.server.response.*

import com.edudesk.backend.routes.authRoutes

fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    
    // Database initialization
    com.edudesk.backend.models.DatabaseFactory.init()

    routing {
        authRoutes()
        
        get("/") {
            call.respondText("EduDesk Backend API is running")
        }
    }
}
