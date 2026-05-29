package com.edudesk.backend.models

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

object DatabaseFactory {
    fun init() {
        val driverClassName = "org.postgresql.Driver"
        val jdbcURL = "jdbc:postgresql://localhost:5432/edudesk" // Should be env var in production
        val database = Database.connect(hikari(jdbcURL, driverClassName))

        transaction(database) {
            SchemaUtils.createMissingTablesAndColumns(
                Users,
                Courses,
                Assignments,
                Enrollments
            )
            
            // Seed a default admin if none exists
            if (User.all().empty()) {
                User.new {
                    name = "Admin Administrator"
                    nim = "admin"
                    email = "admin@edudesk.com"
                    password = BCrypt.hashpw("admin123", BCrypt.gensalt())
                    role = "admin"
                }
            }
        }
    }

    private fun hikari(url: String, driver: String): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = driver
        config.jdbcUrl = url
        config.username = "postgres"
        config.password = "password123"
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
