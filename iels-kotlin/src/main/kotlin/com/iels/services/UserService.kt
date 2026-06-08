package com.iels.services

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable
import com.iels.models.RegisterRequest
import com.iels.models.User
import io.ktor.client.call.*

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

    suspend fun getUsers(): List<User> {
        return try {
            val response = client.get("$baseUrl/users")
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            println("Error fetching users: ${e.message}")
            emptyList()
        }
    }

    @Serializable
    private data class UserStatusRequest(val isActive: Boolean)

    suspend fun updateUserStatus(userId: Int, isActive: Boolean): Boolean {
        return try {
            val response = client.put("$baseUrl/users/$userId/status") {
                contentType(ContentType.Application.Json)
                setBody(UserStatusRequest(isActive))
            }
            response.status == HttpStatusCode.OK
        } catch (e: Exception) {
            println("Error updating user status: ${e.message}")
            false
        }
    }

    suspend fun createUser(request: RegisterRequest): Boolean {
        return try {
            val response = client.post("$baseUrl/auth/register") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            response.status == HttpStatusCode.Created
        } catch (e: Exception) {
            println("Error creating user: ${e.message}")
            false
        }
    }

    suspend fun deleteUser(userId: Int): Boolean {
        return try {
            val response = client.delete("$baseUrl/users/$userId")
            response.status == HttpStatusCode.OK
        } catch (e: Exception) {
            println("Error deleting user: ${e.message}")
            false
        }
    }
}
