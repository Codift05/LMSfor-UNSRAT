package com.edudesk.database

import com.edudesk.config.SupabaseClientManager
import com.edudesk.config.SupabaseConfig
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import kotlinx.coroutines.runBlocking

object DatabaseSetup {
    fun init() {
        // ALWAYS initialize SQLite as fallback (required by Exposed ORM)
        try {
            val dbFile = File("../edudesk.db")
            val dbUrl = "jdbc:sqlite:${dbFile.absolutePath}"
            
            Database.connect(dbUrl, driver = "org.sqlite.JDBC")

            transaction {
                // Create tables if they don't exist
                SchemaUtils.createMissingTablesAndColumns(
                    com.edudesk.models.Users,
                    com.edudesk.models.Courses,
                    com.edudesk.models.Assignments,
                    com.edudesk.models.Enrollments
                )
                
                // Seed default users immediately in the same transaction
                DatabaseSeeder.seedDefaultUsers()
            }
            
            println("✓ SQLite database initialized with default users")
        } catch (e: Exception) {
            println("✗ SQLite initialization error: ${e.message}")
            e.printStackTrace()
        }

        // Try to initialize Supabase connection (optional, cloud backup)
        try {
            if (SupabaseConfig.isConfigured()) {
                println("Initializing Supabase connection...")
                val client = SupabaseClientManager.client
                println("✓ Supabase connected successfully (cloud backup ready)")
                
                // Seed users to Supabase
                runBlocking {
                    DatabaseSeeder.seedToSupabase()
                }
            }
        } catch (e: Exception) {
            println("⚠ Supabase not configured or connection failed: ${e.message}")
        }
    }
}
