package com.edudesk.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

object DatabaseSetup {
    fun init() {
        // Specify the path to the existing SQLite database from the Go project.
        val dbFile = File("../edudesk.db")
        val dbUrl = "jdbc:sqlite:${dbFile.absolutePath}"
        
        Database.connect(dbUrl, driver = "org.sqlite.JDBC")

        transaction {
            // We will create tables if they don't exist, though the Go app likely made them.
            SchemaUtils.createMissingTablesAndColumns(
                com.edudesk.models.Users,
                com.edudesk.models.Courses,
                com.edudesk.models.Assignments,
                com.edudesk.models.Enrollments
            )
        }
    }
}
