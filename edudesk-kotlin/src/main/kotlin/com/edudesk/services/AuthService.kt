package com.edudesk.services

import com.edudesk.models.User
import com.edudesk.models.Users
import com.edudesk.models.SupabaseUser
import com.edudesk.config.SupabaseClientManager
import com.edudesk.config.SupabaseConfig
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.*
import org.mindrot.jbcrypt.BCrypt
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import com.google.gson.Gson
import com.google.gson.JsonObject

class AuthService {
    private val gson = Gson()
    private val supabaseUserService = SupabaseUserService()
    
    // Verifies user login using BCrypt (Local SQLite) - FALLBACK
    fun login(nimEmail: String, pass: String): User? {
        return transaction {
            val user = User.find {
                (Users.nim eq nimEmail) or (Users.email eq nimEmail)
            }.firstOrNull()

            if (user != null && BCrypt.checkpw(pass, user.password)) {
                user
            } else {
                null
            }
        }
    }

    // Login from Supabase Database (PRIMARY METHOD)
    suspend fun loginFromSupabaseDatabase(nimEmail: String, password: String): SupabaseUser? {
        return try {
            if (!SupabaseConfig.isConfigured()) {
                println("⚠ Supabase not configured, falling back to SQLite")
                return null
            }

            // Query user by email or nim from Supabase
            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/users?or=(email.eq.$nimEmail,nim.eq.$nimEmail)"
            
            val response = SupabaseClientManager.client.get(url) {
                header("apikey", SupabaseClientManager.getAnonKey())
                header("Authorization", "Bearer ${SupabaseClientManager.getAnonKey()}")
                header("Content-Type", "application/json")
            }

            if (response.status == HttpStatusCode.OK) {
                val users = gson.fromJson(response.bodyAsText(), Array<JsonObject>::class.java)
                if (users.isNotEmpty()) {
                    val userData = users[0]
                    val storedPassword = userData.get("password")?.asString ?: return null
                    
                    // Verify password with BCrypt
                    if (BCrypt.checkpw(password, storedPassword)) {
                        // Convert to SupabaseUser
                        SupabaseUser(
                            id = (userData.get("id")?.asString ?: ""),
                            email = (userData.get("email")?.asString ?: ""),
                            name = (userData.get("name")?.asString ?: ""),
                            nim = (userData.get("nim")?.asString ?: ""),
                            role = (userData.get("role")?.asString ?: "student"),
                            avatarUrl = (userData.get("avatar_url")?.asString),
                            createdAt = (userData.get("created_at")?.asString ?: "")
                        )
                    } else {
                        println("Invalid password")
                        null
                    }
                } else {
                    println("User not found in Supabase")
                    null
                }
            } else {
                println("Supabase query error: ${response.status}")
                null
            }
        } catch (e: Exception) {
            println("Supabase login error: ${e.message}")
            e.printStackTrace()
            null
        }
    }

    // Register new user on Supabase
    suspend fun registerWithSupabase(email: String, password: String, name: String, nim: String): SupabaseUser? {
        return try {
            if (!SupabaseConfig.isConfigured()) {
                return null
            }

            val url = "${SupabaseClientManager.getSupabaseUrl()}/auth/v1/signup"
            
            val signupBody = mapOf(
                "email" to email,
                "password" to password
            )

            val httpClient = SupabaseClientManager.client
            val response = httpClient.post(url) {
                header("apikey", SupabaseClientManager.getAnonKey())
                header("Authorization", "Bearer ${SupabaseClientManager.getAnonKey()}")
                header("Content-Type", "application/json")
                setBody(gson.toJson(signupBody))
            }

            if (response.status == HttpStatusCode.OK) {
                // Now insert user profile data
                insertUserProfile(email, name, nim)
            } else {
                println("Registration error: ${response.status}")
                null
            }
        } catch (e: Exception) {
            println("Supabase registration error: ${e.message}")
            null
        }
    }

    // Insert user profile data
    private suspend fun insertUserProfile(email: String, name: String, nim: String): SupabaseUser? {
        return try {
            if (!SupabaseConfig.isConfigured()) {
                return null
            }

            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/users"
            
            val userData = mapOf(
                "email" to email,
                "name" to name,
                "nim" to nim,
                "role" to "student"
            )

            val httpClient = SupabaseClientManager.client
            val response = httpClient.post(url) {
                header("apikey", SupabaseClientManager.getAnonKey())
                header("Authorization", "Bearer ${SupabaseClientManager.getAnonKey()}")
                header("Content-Type", "application/json")
                setBody(gson.toJson(userData))
            }

            if (response.status == HttpStatusCode.Created) {
                getUserByEmail(email)
            } else {
                println("Insert error: ${response.status}")
                null
            }
        } catch (e: Exception) {
            println("Error inserting user: ${e.message}")
            null
        }
    }

    // Get user by email
    suspend fun getUserByEmail(email: String): SupabaseUser? {
        return try {
            if (!SupabaseConfig.isConfigured()) return null

            val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/users?email=eq.$email"

            val httpClient = SupabaseClientManager.client
            val response = httpClient.get(url) {
                header("apikey", SupabaseClientManager.getAnonKey())
                header("Authorization", "Bearer ${SupabaseClientManager.getAnonKey()}")
                header("Content-Type", "application/json")
            }

            if (response.status == HttpStatusCode.OK) {
                val responseText = response.bodyAsText()
                val users = gson.fromJson(responseText, Array<JsonObject>::class.java)
                
                if (users.isNotEmpty()) {
                    val userJson = users[0]
                    SupabaseUser(
                        id = userJson.get("id")?.asString ?: "",
                        nim = userJson.get("nim")?.asString ?: "",
                        email = userJson.get("email")?.asString ?: "",
                        name = userJson.get("name")?.asString ?: "",
                        role = userJson.get("role")?.asString ?: "student",
                        avatarUrl = userJson.get("avatar_url")?.asString,
                        createdAt = userJson.get("created_at")?.asString ?: ""
                    )
                } else {
                    null
                }
            } else {
                null
            }
        } catch (e: Exception) {
            println("Error fetching user: ${e.message}")
            null
        }
    }

    // Logout (Clear session on client side - Supabase handles server-side)
    suspend fun logoutFromSupabase() {
        try {
            println("Logged out from Supabase")
        } catch (e: Exception) {
            println("Logout error: ${e.message}")
        }
    }
}
