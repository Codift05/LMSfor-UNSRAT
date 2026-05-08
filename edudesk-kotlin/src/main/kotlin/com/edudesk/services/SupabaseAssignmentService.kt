package com.edudesk.services

import com.edudesk.models.SupabaseAssignment
import com.edudesk.config.SupabaseClientManager
import com.edudesk.config.SupabaseConfig
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import com.google.gson.Gson
import com.google.gson.JsonObject

class SupabaseAssignmentService {
    private val gson = Gson()
    private val anonKey get() = SupabaseClientManager.getAnonKey()

    private fun HttpRequestBuilder.setupHeaders() {
        header("apikey", anonKey)
        header("Authorization", "Bearer $anonKey")
        header("Content-Type", "application/json")
    }

    suspend fun getAssignmentById(assignmentId: String): SupabaseAssignment? {
        if (!SupabaseConfig.isConfigured()) return null
        try {
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/assignments?id=eq.$assignmentId"
            val response = SupabaseClientManager.client.get(url) { setupHeaders() }
            if (response.status == HttpStatusCode.OK) {
                val assignments = gson.fromJson(response.bodyAsText(), Array<JsonObject>::class.java)
                return if (assignments.isNotEmpty()) parseAssignmentFromJson(assignments[0]) else null
            }
        } catch (e: Exception) {
            println("Error fetching assignment: ${e.message}")
        }
        return null
    }

    suspend fun getAssignmentsByCourse(courseId: String): List<SupabaseAssignment> {
        if (!SupabaseConfig.isConfigured()) return emptyList()
        try {
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/assignments?course_id=eq.$courseId"
            val response = SupabaseClientManager.client.get(url) { setupHeaders() }
            if (response.status == HttpStatusCode.OK) {
                val assignments = gson.fromJson(response.bodyAsText(), Array<JsonObject>::class.java)
                return assignments.mapNotNull { parseAssignmentFromJson(it) }
            }
        } catch (e: Exception) {
            println("Error fetching assignments: ${e.message}")
        }
        return emptyList()
    }

    suspend fun getAllAssignments(): List<SupabaseAssignment> {
        if (!SupabaseConfig.isConfigured()) return emptyList()
        try {
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/assignments"
            val response = SupabaseClientManager.client.get(url) { setupHeaders() }
            if (response.status == HttpStatusCode.OK) {
                val assignments = gson.fromJson(response.bodyAsText(), Array<JsonObject>::class.java)
                return assignments.mapNotNull { parseAssignmentFromJson(it) }
            }
        } catch (e: Exception) {
            println("Error fetching all assignments: ${e.message}")
        }
        return emptyList()
    }

    suspend fun createAssignment(assignment: Map<String, Any>): SupabaseAssignment? {
        if (!SupabaseConfig.isConfigured()) return null
        try {
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/assignments"
            val response = SupabaseClientManager.client.post(url) {
                setupHeaders()
                setBody(gson.toJson(assignment))
            }
            if (response.status == HttpStatusCode.Created) {
                val id = assignment["id"] as? String ?: return null
                return getAssignmentById(id)
            }
        } catch (e: Exception) {
            println("Error creating assignment: ${e.message}")
        }
        return null
    }

    suspend fun updateAssignment(assignmentId: String, updates: Map<String, Any>): Boolean {
        if (!SupabaseConfig.isConfigured()) return false
        try {
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/assignments?id=eq.$assignmentId"
            val response = SupabaseClientManager.client.patch(url) {
                setupHeaders()
                setBody(gson.toJson(updates))
            }
            return response.status == HttpStatusCode.OK || response.status == HttpStatusCode.NoContent
        } catch (e: Exception) {
            println("Error updating assignment: ${e.message}")
        }
        return false
    }

    suspend fun deleteAssignment(assignmentId: String): Boolean {
        if (!SupabaseConfig.isConfigured()) return false
        try {
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/assignments?id=eq.$assignmentId"
            val response = SupabaseClientManager.client.delete(url) { setupHeaders() }
            return response.status == HttpStatusCode.OK || response.status == HttpStatusCode.NoContent
        } catch (e: Exception) {
            println("Error deleting assignment: ${e.message}")
        }
        return false
    }

    private fun parseAssignmentFromJson(json: JsonObject): SupabaseAssignment? {
        return try {
            SupabaseAssignment(
                id = json.get("id")?.asString ?: return null,
                courseId = json.get("course_id")?.asString ?: "",
                title = json.get("title")?.asString ?: "",
                description = json.get("description")?.asString ?: "",
                dueDate = json.get("due_date")?.asString ?: "",
                createdAt = json.get("created_at")?.asString ?: ""
            )
        } catch (e: Exception) {
            null
        }
    }
}
