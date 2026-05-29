package com.edudesk.services

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable

class UserService {
    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
    private val baseUrl = "http://localhost:8081"

    @Serializable
    private data class UpdateProfileRequest(
        val name: String,
        val nimEmail: String,
        val email: String,
        val password: String? = null
    )

    suspend fun updateProfile(
        userId: Int,
        name: String,
        nimEmail: String,
        email: String,
        password: String? = null
    ): Boolean {
        return try {
            val response = client.put("$baseUrl/users/$userId") {
                contentType(ContentType.Application.Json)
                setBody(UpdateProfileRequest(name, nimEmail, email, password))
            }
            response.status == HttpStatusCode.OK
        } catch (e: Exception) {
            println("Error updating profile: ${e.message}")
            false
        }
    }
}
