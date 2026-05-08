package com.edudesk.config

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * Manages Supabase HTTP Client for REST API calls
 */
object SupabaseClientManager {
    private var _client: HttpClient? = null

    /**
     * Get or create the Ktor HTTP Client for Supabase API calls
     */
    val client: HttpClient
        get() {
            if (_client == null) {
                _client = HttpClient(OkHttp) {
                    install(ContentNegotiation) {
                        json(Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                        })
                    }
                }
            }
            return _client!!
        }

    /**
     * Get Supabase base URL from config
     */
    fun getSupabaseUrl(): String = SupabaseConfig.supabaseUrl

    /**
     * Get Supabase Anon Key for client-side requests
     */
    fun getAnonKey(): String = SupabaseConfig.anonKey

    /**
     * Get Supabase Service Key for server-side requests (Be careful!)
     */
    fun getServiceKey(): String = SupabaseConfig.serviceKey

    /**
     * Build authorization header for API requests
     */
    fun getAuthHeaders(): Map<String, String> {
        val token = SupabaseConfig.anonKey
        return mapOf(
            "apikey" to token,
            "Authorization" to "Bearer $token",
            "Content-Type" to "application/json"
        )
    }

    /**
     * Disconnect and cleanup resources
     */
    fun disconnect() {
        _client?.close()
        _client = null
    }
}
