package com.edudesk.services

import com.edudesk.models.SupabaseCourse
import com.edudesk.config.SupabaseClientManager
import com.edudesk.config.SupabaseConfig
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import com.google.gson.Gson
import com.google.gson.JsonObject

class SupabaseCourseService {
    private val gson = Gson()
    private val anonKey get() = SupabaseClientManager.getAnonKey()

    private fun HttpRequestBuilder.setupHeaders() {
        header("apikey", anonKey)
        header("Authorization", "Bearer $anonKey")
        header("Content-Type", "application/json")
    }

    suspend fun getCourseById(courseId: String): SupabaseCourse? {
        if (!SupabaseConfig.isConfigured()) return null
        try {
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/courses?id=eq.$courseId"
            val response = SupabaseClientManager.client.get(url) { setupHeaders() }
            if (response.status == HttpStatusCode.OK) {
                val courses = gson.fromJson(response.bodyAsText(), Array<JsonObject>::class.java)
                return if (courses.isNotEmpty()) parseCourseFromJson(courses[0]) else null
            }
        } catch (e: Exception) {
            println("Error fetching course: ${e.message}")
        }
        return null
    }

    suspend fun getAllCourses(): List<SupabaseCourse> {
        if (!SupabaseConfig.isConfigured()) return emptyList()
        try {
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/courses"
            val response = SupabaseClientManager.client.get(url) { setupHeaders() }
            if (response.status == HttpStatusCode.OK) {
                val courses = gson.fromJson(response.bodyAsText(), Array<JsonObject>::class.java)
                return courses.mapNotNull { parseCourseFromJson(it) }
            }
        } catch (e: Exception) {
            println("Error fetching all courses: ${e.message}")
        }
        return emptyList()
    }

    suspend fun getCoursesByInstructor(instructorId: String): List<SupabaseCourse> {
        if (!SupabaseConfig.isConfigured()) return emptyList()
        try {
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/courses?instructor_id=eq.$instructorId"
            val response = SupabaseClientManager.client.get(url) { setupHeaders() }
            if (response.status == HttpStatusCode.OK) {
                val courses = gson.fromJson(response.bodyAsText(), Array<JsonObject>::class.java)
                return courses.mapNotNull { parseCourseFromJson(it) }
            }
        } catch (e: Exception) {
            println("Error fetching courses: ${e.message}")
        }
        return emptyList()
    }

    suspend fun createCourse(course: Map<String, Any>): SupabaseCourse? {
        if (!SupabaseConfig.isConfigured()) return null
        try {
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/courses"
            val response = SupabaseClientManager.client.post(url) {
                setupHeaders()
                setBody(gson.toJson(course))
            }
            if (response.status == HttpStatusCode.Created) {
                val id = course["id"] as? String ?: return null
                return getCourseById(id)
            }
        } catch (e: Exception) {
            println("Error creating course: ${e.message}")
        }
        return null
    }

    suspend fun updateCourse(courseId: String, updates: Map<String, Any>): Boolean {
        if (!SupabaseConfig.isConfigured()) return false
        try {
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/courses?id=eq.$courseId"
            val response = SupabaseClientManager.client.patch(url) {
                setupHeaders()
                setBody(gson.toJson(updates))
            }
            return response.status == HttpStatusCode.OK || response.status == HttpStatusCode.NoContent
        } catch (e: Exception) {
            println("Error updating course: ${e.message}")
        }
        return false
    }

    suspend fun deleteCourse(courseId: String): Boolean {
        if (!SupabaseConfig.isConfigured()) return false
        try {
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/courses?id=eq.$courseId"
            val response = SupabaseClientManager.client.delete(url) { setupHeaders() }
            return response.status == HttpStatusCode.OK || response.status == HttpStatusCode.NoContent
        } catch (e: Exception) {
            println("Error deleting course: ${e.message}")
        }
        return false
    }

    private fun parseCourseFromJson(json: JsonObject): SupabaseCourse? {
        return try {
            SupabaseCourse(
                id = json.get("id")?.asString ?: return null,
                code = json.get("code")?.asString ?: "",
                name = json.get("name")?.asString ?: "",
                description = json.get("description")?.asString ?: "",
                instructorId = json.get("instructor_id")?.asString ?: "",
                credits = json.get("credits")?.asInt ?: 3,
                createdAt = json.get("created_at")?.asString ?: ""
            )
        } catch (e: Exception) {
            null
        }
    }
}
