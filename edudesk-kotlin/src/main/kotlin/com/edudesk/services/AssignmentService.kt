package com.edudesk.services

import com.edudesk.models.Assignment
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable

class AssignmentService {
    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
    private val baseUrl = "http://localhost:8081"

    @Serializable
    private data class AddAssignmentRequest(val title: String, val desc: String, val deadline: String, val courseId: Int, val userId: Int)

    suspend fun getAssignmentsForUser(userId: Int): List<Assignment> {
        return try {
            val response = client.get("$baseUrl/assignments?userId=$userId")
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            println("Error fetching assignments: ${e.message}")
            emptyList()
        }
    }

    suspend fun addAssignment(title: String, desc: String, deadline: String, courseId: Int, userId: Int) {
        try {
            client.post("$baseUrl/assignments") {
                contentType(ContentType.Application.Json)
                setBody(AddAssignmentRequest(title, desc, deadline, courseId, userId))
            }
        } catch (e: Exception) {
            println("Error adding assignment: ${e.message}")
        }
    }

    suspend fun markDone(assignmentId: Int) {
        try {
            client.put("$baseUrl/assignments/$assignmentId/done")
        } catch (e: Exception) {
            println("Error marking assignment done: ${e.message}")
        }
    }

    suspend fun deleteAssignment(assignmentId: Int) {
        try {
            client.delete("$baseUrl/assignments/$assignmentId")
        } catch (e: Exception) {
            println("Error deleting assignment: ${e.message}")
        }
    }
}
