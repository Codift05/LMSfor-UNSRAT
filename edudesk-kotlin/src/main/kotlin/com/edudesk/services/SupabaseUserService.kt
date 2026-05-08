package com.edudesk.services

import com.edudesk.models.SupabaseUser
import com.edudesk.config.SupabaseClientManager
import com.edudesk.config.SupabaseConfig
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import com.google.gson.Gson
import com.google.gson.JsonObject

class SupabaseUserService {
    private val gson = Gson()
    private val anonKey get() = SupabaseClientManager.getAnonKey()

    private fun HttpRequestBuilder.setupHeaders() {
        header("apikey", anonKey)
        header("Authorization", "Bearer $anonKey")
        header("Content-Type", "application/json")
    }

    suspend fun getUserById(userId: String): SupabaseUser? {
        if (!SupabaseConfig.isConfigured()) return null
        try {
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/users?id=eq.$userId"
            val response = SupabaseClientManager.client.get(url) { setupHeaders() }
            if (response.status == HttpStatusCode.OK) {
                val users = gson.fromJson(response.bodyAsText(), Array<JsonObject>::class.java)
                return if (users.isNotEmpty()) parseUserFromJson(users[0]) else null
            }
        } catch (e: Exception) {
            println("Error fetching user: ${e.message}")
        }
        return null
    }

    suspend fun getUserByEmail(email: String): SupabaseUser? {
        if (!SupabaseConfig.isConfigured()) return null
        try {
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/users?email=eq.$email"
            val response = SupabaseClientManager.client.get(url) { setupHeaders() }
            if (response.status == HttpStatusCode.OK) {
                val users = gson.fromJson(response.bodyAsText(), Array<JsonObject>::class.java)
                return if (users.isNotEmpty()) parseUserFromJson(users[0]) else null
            }
        } catch (e: Exception) {
            println("Error fetching user by email: ${e.message}")
        }
        return null
    }

    suspend fun getAllUsers(): List<SupabaseUser> {
        if (!SupabaseConfig.isConfigured()) return emptyList()
        try {
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/users"
            val response = SupabaseClientManager.client.get(url) { setupHeaders() }
            if (response.status == HttpStatusCode.OK) {
                val users = gson.fromJson(response.bodyAsText(), Array<JsonObject>::class.java)
                return users.mapNotNull { parseUserFromJson(it) }
            }
        } catch (e: Exception) {
            println("Error fetching all users: ${e.message}")
        }
        return emptyList()
    }

    suspend fun updateUser(userId: String, updates: Map<String, Any>): Boolean {
        if (!SupabaseConfig.isConfigured()) return false
        try {
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/users?id=eq.$userId"
            val response = SupabaseClientManager.client.patch(url) {
                setupHeaders()
                setBody(gson.toJson(updates))
            }
            return response.status == HttpStatusCode.OK || response.status == HttpStatusCode.NoContent
        } catch (e: Exception) {
            println("Error updating user: ${e.message}")
        }
        return false
    }

    suspend fun deleteUser(userId: String): Boolean {
        if (!SupabaseConfig.isConfigured()) return false
        try {
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/users?id=eq.$userId"
            val response = SupabaseClientManager.client.delete(url) { setupHeaders() }
            return response.status == HttpStatusCode.OK || response.status == HttpStatusCode.NoContent
        } catch (e: Exception) {
            println("Error deleting user: ${e.message}")
        }
        return false
    }

    suspend fun getUsersByRole(role: String): List<SupabaseUser> {
        if (!SupabaseConfig.isConfigured()) return emptyList()
        try {
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/users?role=eq.$role"
            val response = SupabaseClientManager.client.get(url) { setupHeaders() }
            if (response.status == HttpStatusCode.OK) {
                val users = gson.fromJson(response.bodyAsText(), Array<JsonObject>::class.java)
                return users.mapNotNull { parseUserFromJson(it) }
            }
        } catch (e: Exception) {
            println("Error fetching users by role: ${e.message}")
        }
        return emptyList()
    }

    private fun parseUserFromJson(json: JsonObject): SupabaseUser? {
        return try {
            SupabaseUser(
                id = json.get("id")?.asString ?: return null,
                nim = json.get("nim")?.asString ?: "",
                email = json.get("email")?.asString ?: "",
                name = json.get("name")?.asString ?: "",
                role = json.get("role")?.asString ?: "student",
                avatarUrl = json.get("avatar_url")?.asString,
                createdAt = json.get("created_at")?.asString ?: ""
            )
        } catch (e: Exception) {
            null
        }
    }
}
