package com.edudesk.database

import com.edudesk.models.Users
import com.edudesk.services.SupabaseUserService
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.mindrot.jbcrypt.BCrypt
import com.google.gson.Gson
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import com.edudesk.config.SupabaseClientManager
import com.edudesk.config.SupabaseConfig

object DatabaseSeeder {
    private val gson = Gson()
    
    fun seedDefaultUsers() {
        try {
            transaction {
                // Check if default users already exist
                val adminExists = Users.selectAll().where { Users.email eq "admin@edudesk.com" }.count() > 0
                val instructorExists = Users.selectAll().where { Users.email eq "instructor@edudesk.com" }.count() > 0
                val student1Exists = Users.selectAll().where { Users.email eq "student@edudesk.com" }.count() > 0
                val student2Exists = Users.selectAll().where { Users.email eq "student2@edudesk.com" }.count() > 0

                var createdCount = 0

                // Admin User - use raw insert
                if (!adminExists) {
                    Users.insert {
                        it[name] = "Admin User"
                        it[nim] = "ADMIN001"
                        it[email] = "admin@edudesk.com"
                        it[password] = BCrypt.hashpw("admin123", BCrypt.gensalt())
                        it[role] = "admin"
                    }
                    println("✓ Created admin: admin@edudesk.com / admin123")
                    createdCount++
                }

                // Instructor User
                if (!instructorExists) {
                    Users.insert {
                        it[name] = "Instruktur Kotlin"
                        it[nim] = "INS001"
                        it[email] = "instructor@edudesk.com"
                        it[password] = BCrypt.hashpw("instructor123", BCrypt.gensalt())
                        it[role] = "instructor"
                    }
                    println("✓ Created instructor: instructor@edudesk.com / instructor123")
                    createdCount++
                }

                // Student User 1
                if (!student1Exists) {
                    Users.insert {
                        it[name] = "Student One"
                        it[nim] = "STU001"
                        it[email] = "student@edudesk.com"
                        it[password] = BCrypt.hashpw("student123", BCrypt.gensalt())
                        it[role] = "student"
                    }
                    println("✓ Created student: student@edudesk.com / student123")
                    createdCount++
                }

                // Student User 2
                if (!student2Exists) {
                    Users.insert {
                        it[name] = "Student Two"
                        it[nim] = "STU002"
                        it[email] = "student2@edudesk.com"
                        it[password] = BCrypt.hashpw("student123", BCrypt.gensalt())
                        it[role] = "student"
                    }
                    println("✓ Created student 2: student2@edudesk.com / student123")
                    createdCount++
                }

                if (createdCount > 0) {
                    println("\n===== DEFAULT CREDENTIALS =====")
                    println("ADMIN:")
                    println("  Email: admin@edudesk.com")
                    println("  Password: admin123")
                    println("\nINSTRUCTOR:")
                    println("  Email: instructor@edudesk.com")
                    println("  Password: instructor123")
                    println("\nSTUDENT:")
                    println("  Email: student@edudesk.com")
                    println("  Password: student123")
                    println("\nSTUDENT 2:")
                    println("  Email: student2@edudesk.com")
                    println("  Password: student123")
                    println("================================\n")
                } else {
                    println("✓ All default users already exist")
                }
            }
        } catch (e: Exception) {
            println("⚠ Error seeding users: ${e.message}")
        }
    }

    // Seed default users to Supabase
    suspend fun seedToSupabase() {
        if (!SupabaseConfig.isConfigured()) {
            println("⚠ Supabase not configured, skipping cloud seed")
            return
        }

        try {
            val defaultUsers = listOf(
                mapOf(
                    "name" to "Admin User",
                    "nim" to "ADMIN001",
                    "email" to "admin@edudesk.com",
                    "password" to BCrypt.hashpw("admin123", BCrypt.gensalt()),
                    "role" to "admin"
                ),
                mapOf(
                    "name" to "Instruktur Kotlin",
                    "nim" to "INS001",
                    "email" to "instructor@edudesk.com",
                    "password" to BCrypt.hashpw("instructor123", BCrypt.gensalt()),
                    "role" to "instructor"
                ),
                mapOf(
                    "name" to "Student One",
                    "nim" to "STU001",
                    "email" to "student@edudesk.com",
                    "password" to BCrypt.hashpw("student123", BCrypt.gensalt()),
                    "role" to "student"
                ),
                mapOf(
                    "name" to "Student Two",
                    "nim" to "STU002",
                    "email" to "student2@edudesk.com",
                    "password" to BCrypt.hashpw("student123", BCrypt.gensalt()),
                    "role" to "student"
                )
            )

            for (user in defaultUsers) {
                try {
                    val url = "${SupabaseClientManager.getSupabaseUrl()}/rest/v1/users"
                    val response = SupabaseClientManager.client.post(url) {
                        header("apikey", SupabaseClientManager.getAnonKey())
                        header("Authorization", "Bearer ${SupabaseClientManager.getAnonKey()}")
                        header("Content-Type", "application/json")
                        setBody(gson.toJson(user))
                    }

                    if (response.status == HttpStatusCode.Created || response.status == HttpStatusCode.OK) {
                        println("✓ Seeded to Supabase: ${user["email"]}")
                    } else if (response.status == HttpStatusCode.Conflict) {
                        println("ℹ Supabase: ${user["email"]} already exists")
                    } else {
                        println("⚠ Supabase seed error for ${user["email"]}: ${response.status}")
                    }
                } catch (e: Exception) {
                    println("⚠ Error seeding ${user["email"]} to Supabase: ${e.message}")
                }
            }

            println("✓ Supabase seed completed")
        } catch (e: Exception) {
            println("⚠ Error during Supabase seed: ${e.message}")
        }
    }
}
