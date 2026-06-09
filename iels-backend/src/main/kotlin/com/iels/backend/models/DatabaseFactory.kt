package com.iels.backend.models

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

object DatabaseFactory {
    fun init(
        jdbcUrlOverride: String? = null,
        usernameOverride: String? = null,
        passwordOverride: String? = null
    ) {
        val driverClassName = "org.postgresql.Driver"
        val jdbcURL = jdbcUrlOverride ?: "jdbc:postgresql://localhost:5432/iels"
        val user = usernameOverride ?: "postgres"
        val pass = passwordOverride ?: "password123"
        val database = Database.connect(hikari(jdbcURL, driverClassName, user, pass))

        transaction(database) {
            SchemaUtils.createMissingTablesAndColumns(
                Users,
                Exams,
                Questions,
                ExamResults,
                ExamSessions
            )
            
            // Seed a default admin if none exists
            if (User.find { Users.nim eq "admin" }.empty()) {
                User.new {
                    name = "Admin Administrator"
                    nim = "admin"
                    email = "admin@iels.com"
                    password = BCrypt.hashpw("admin123", BCrypt.gensalt())
                    role = "admin"
                }
            }
            
            // Seed a default student
            if (User.find { Users.nim eq "siswa" }.empty()) {
                User.new {
                    name = "Siswa Teladan"
                    nim = "siswa"
                    email = "siswa@iels.com"
                    password = BCrypt.hashpw("siswa123", BCrypt.gensalt())
                    role = "student"
                }
            }
        }
    }

    private fun hikari(url: String, driver: String, user: String, pass: String): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = driver
        config.jdbcUrl = url
        config.username = user
        config.password = pass
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        withContext(Dispatchers.IO) {
            transaction {
                kotlinx.coroutines.runBlocking {
                    block()
                }
            }
        }
}
