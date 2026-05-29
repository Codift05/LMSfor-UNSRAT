package com.edudesk.services

import com.edudesk.models.User
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable

class AuthService {
    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }
    
    private val baseUrl = "http://localhost:8081"

    @Serializable
    private data class LoginRequest(val nimEmail: String, val pass: String)

    @Serializable
    private data class RegisterRequest(val email: String, val pass: String, val name: String, val nim: String, val role: String = "student")

    suspend fun login(nimEmail: String, pass: String): User? {
        return try {
            val response = client.post("$baseUrl/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(nimEmail, pass))
            }

            if (response.status == HttpStatusCode.OK) {
                response.body<User>()
            } else {
                println("Login failed: ${response.status}")
                null
            }
        } catch (e: Exception) {
            println("Login error: ${e.message}")
            null
        }
    }

    suspend fun register(email: String, password: String, name: String, nim: String): User? {
        return try {
            val response = client.post("$baseUrl/auth/register") {
                contentType(ContentType.Application.Json)
                setBody(RegisterRequest(email, password, name, nim))
            }

            if (response.status == HttpStatusCode.Created) {
                response.body<User>()
            } else {
                println("Registration failed: ${response.status}")
                null
            }
        } catch (e: Exception) {
            println("Registration error: ${e.message}")
            null
        }
    }

    suspend fun logout() {
        // Just clear local session (SessionManager is usually used)
        println("Logged out locally")
    }
}
