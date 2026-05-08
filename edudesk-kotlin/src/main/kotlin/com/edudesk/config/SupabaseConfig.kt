package com.edudesk.config

import io.github.cdimascio.dotenv.dotenv

object SupabaseConfig {
    private val env = dotenv {
        ignoreIfMissing = true
        directory = "."
    }

    val supabaseUrl: String = env["SUPABASE_URL"] ?: throw IllegalStateException("SUPABASE_URL not set in .env")
    val anonKey: String = env["SUPABASE_ANON_KEY"] ?: throw IllegalStateException("SUPABASE_ANON_KEY not set in .env")
    val serviceKey: String = env["SUPABASE_SERVICE_KEY"] ?: throw IllegalStateException("SUPABASE_SERVICE_KEY not set in .env")
    
    fun isConfigured(): Boolean {
        return supabaseUrl.isNotEmpty() && anonKey.isNotEmpty()
    }
}
