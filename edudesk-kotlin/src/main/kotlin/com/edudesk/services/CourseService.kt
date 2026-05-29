package com.edudesk.services

import com.edudesk.models.Course
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable

class CourseService {
    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
    private val baseUrl = "http://localhost:8081"

    @Serializable
    private data class AddCourseRequest(val name: String, val code: String, val description: String, val userId: Int)

    suspend fun getCoursesForUser(userId: Int): List<Course> {
        return try {
            val response = client.get("$baseUrl/courses?userId=$userId")
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            println("Error fetching courses: ${e.message}")
            emptyList()
        }
    }

    suspend fun addCourse(name: String, code: String, desc: String, userId: Int) {
        try {
            client.post("$baseUrl/courses") {
                contentType(ContentType.Application.Json)
                setBody(AddCourseRequest(name, code, desc, userId))
            }
        } catch (e: Exception) {
            println("Error adding course: ${e.message}")
        }
    }

    suspend fun deleteCourse(courseId: Int) {
        try {
            client.delete("$baseUrl/courses/$courseId")
        } catch (e: Exception) {
            println("Error deleting course: ${e.message}")
        }
    }
}
