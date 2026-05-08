package com.edudesk.services

import com.edudesk.models.SupabaseEnrollment
import com.edudesk.config.SupabaseClientManager
import com.edudesk.config.SupabaseConfig
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import com.google.gson.Gson
import com.google.gson.JsonObject

class SupabaseEnrollmentService {
    private val gson = Gson()
    private val anonKey get() = SupabaseClientManager.getAnonKey()

    private fun HttpRequestBuilder.setupHeaders() {
        header("apikey", anonKey)
        header("Authorization", "Bearer $anonKey")
        header("Content-Type", "application/json")
    }

    suspend fun getEnrollmentsByUser(userId: String): List<SupabaseEnrollment> {
        if (!SupabaseConfig.isConfigured()) return emptyList()
        try {
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/enrollments?user_id=eq.$userId"
            val response = SupabaseClientManager.client.get(url) { setupHeaders() }
            if (response.status == HttpStatusCode.OK) {
                val enrollments = gson.fromJson(response.bodyAsText(), Array<JsonObject>::class.java)
                return enrollments.mapNotNull { parseEnrollmentFromJson(it) }
            }
        } catch (e: Exception) {
            println("Error fetching enrollments: ${e.message}")
        }
        return emptyList()
    }

    suspend fun getEnrollmentsByCourse(courseId: String): List<SupabaseEnrollment> {
        if (!SupabaseConfig.isConfigured()) return emptyList()
        try {
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/enrollments?course_id=eq.$courseId"
            val response = SupabaseClientManager.client.get(url) { setupHeaders() }
            if (response.status == HttpStatusCode.OK) {
                val enrollments = gson.fromJson(response.bodyAsText(), Array<JsonObject>::class.java)
                return enrollments.mapNotNull { parseEnrollmentFromJson(it) }
            }
        } catch (e: Exception) {
            println("Error fetching enrollments by course: ${e.message}")
        }
        return emptyList()
    }

    suspend fun enrollUserInCourse(userId: String, courseId: String): SupabaseEnrollment? {
        if (!SupabaseConfig.isConfigured()) return null
        try {
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/enrollments"
            val enrollmentData = mapOf(
                "user_id" to userId,
                "course_id" to courseId,
                "status" to "active"
            )
            val response = SupabaseClientManager.client.post(url) {
                setupHeaders()
                setBody(gson.toJson(enrollmentData))
            }
            if (response.status == HttpStatusCode.Created) {
                val enrollments = gson.fromJson(response.bodyAsText(), Array<JsonObject>::class.java)
                return if (enrollments.isNotEmpty()) parseEnrollmentFromJson(enrollments[0]) else null
            }
        } catch (e: Exception) {
            println("Error enrolling user: ${e.message}")
        }
        return null
    }

    suspend fun unenrollUserFromCourse(userId: String, courseId: String): Boolean {
        if (!SupabaseConfig.isConfigured()) return false
        try {
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/enrollments?user_id=eq.$userId&course_id=eq.$courseId"
            val response = SupabaseClientManager.client.delete(url) { setupHeaders() }
            return response.status == HttpStatusCode.OK || response.status == HttpStatusCode.NoContent
        } catch (e: Exception) {
            println("Error unenrolling user: ${e.message}")
        }
        return false
    }

    suspend fun updateEnrollmentStatus(userId: String, courseId: String, status: String): Boolean {
        if (!SupabaseConfig.isConfigured()) return false
        try {
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/enrollments?user_id=eq.$userId&course_id=eq.$courseId"
            val updateData = mapOf("status" to status)
            val response = SupabaseClientManager.client.patch(url) {
                setupHeaders()
                setBody(gson.toJson(updateData))
            }
            return response.status == HttpStatusCode.OK || response.status == HttpStatusCode.NoContent
        } catch (e: Exception) {
            println("Error updating enrollment status: ${e.message}")
        }
        return false
    }

    private fun parseEnrollmentFromJson(json: JsonObject): SupabaseEnrollment? {
        return try {
            SupabaseEnrollment(
                id = json.get("id")?.asString ?: return null,
                userId = json.get("user_id")?.asString ?: "",
                courseId = json.get("course_id")?.asString ?: "",
                enrolledAt = json.get("enrolled_at")?.asString ?: "",
                status = json.get("status")?.asString ?: "active"
            )
        } catch (e: Exception) {
            null
        }
    }
}
