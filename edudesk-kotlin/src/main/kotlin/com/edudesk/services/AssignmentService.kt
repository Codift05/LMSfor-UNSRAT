package com.edudesk.services

import com.edudesk.models.Assignment
import com.edudesk.models.Assignments
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.*

class AssignmentService {

    fun getAssignmentsForUser(userId: Int): List<Assignment> {
        return transaction {
            // We preload the course to avoid N+1 query issues if necessary later
            Assignment.find { Assignments.userId eq userId }.toList()
        }
    }

    fun addAssignment(title: String, desc: String, deadline: String, courseId: Int, userId: Int) {
        transaction {
            Assignment.new {
                this.title = title
                this.description = desc
                this.deadline = deadline
                this.course = com.edudesk.models.Course[courseId]
                this.user = com.edudesk.models.User[userId]
                this.isDone = false
            }
        }
    }

    fun markDone(assignmentId: Int) {
        transaction {
            val assignment = Assignment.findById(assignmentId)
            assignment?.isDone = true
        }
    }

    fun deleteAssignment(assignmentId: Int) {
        transaction {
            val assignment = Assignment.findById(assignmentId)
            assignment?.delete()
        }
    }
}
