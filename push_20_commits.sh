#!/bin/bash

# 1
git add edudesk-backend/build.gradle.kts edudesk-backend/settings.gradle.kts
git commit -m "chore: Initialize edudesk-backend Ktor project"

# 2
git add edudesk-backend/src/main/kotlin/com/edudesk/backend/Application.kt
git commit -m "feat: Add Ktor Netty application entry point on port 8081"

# 3
git add edudesk-backend/src/main/kotlin/com/edudesk/backend/models/Users.kt edudesk-backend/src/main/kotlin/com/edudesk/backend/models/Courses.kt
git commit -m "feat: Define Users and Courses database schema"

# 4
git add edudesk-backend/src/main/kotlin/com/edudesk/backend/models/Enrollments.kt edudesk-backend/src/main/kotlin/com/edudesk/backend/models/Assignments.kt edudesk-backend/src/main/kotlin/com/edudesk/backend/models/Submissions.kt
git commit -m "feat: Define Enrollments, Assignments, and Submissions database schema"

# 5
git add edudesk-backend/src/main/kotlin/com/edudesk/backend/models/DatabaseFactory.kt
git commit -m "feat: Implement DatabaseFactory with Exposed ORM and HikariCP"

# 6
git add edudesk-backend/src/main/kotlin/com/edudesk/backend/routes/AuthRoutes.kt
git commit -m "feat: Implement Auth routes for login and register"

# 7
git add docker-compose.yml
git commit -m "chore: Add Docker Compose for PostgreSQL database"

# 8
git rm -q old-golang-backup/models/assignment.go old-golang-backup/models/course.go old-golang-backup/models/submission.go old-golang-backup/models/user.go
git commit -m "refactor: Remove legacy Go models"

# 9
git rm -q old-golang-backup/services/assignment_service.go old-golang-backup/services/auth_service.go old-golang-backup/services/course_service.go
git commit -m "refactor: Remove legacy Go services"

# 10
git rm -q old-golang-backup/ui/app_tabs.go old-golang-backup/ui/assignment.go old-golang-backup/ui/course.go old-golang-backup/ui/dashboard.go old-golang-backup/ui/login.go old-golang-backup/ui/profile.go old-golang-backup/database/sqlite.go old-golang-backup/main.go go.mod go.sum
git commit -m "refactor: Remove legacy Go UI and core files"

# 11
git rm -q edudesk-kotlin/src/main/kotlin/com/edudesk/config/SupabaseClient.kt edudesk-kotlin/src/main/kotlin/com/edudesk/config/SupabaseConfig.kt
git commit -m "refactor: Remove Supabase configuration files"

# 12
git rm -q edudesk-kotlin/src/main/kotlin/com/edudesk/database/DatabaseSeeder.kt edudesk-kotlin/src/main/kotlin/com/edudesk/database/DatabaseSetup.kt
git commit -m "refactor: Remove SQLite and local database setup"

# 13
git rm -q edudesk-kotlin/src/main/kotlin/com/edudesk/models/SupabaseModels.kt
git commit -m "refactor: Remove Supabase-specific models"

# 14
git rm -q edudesk-kotlin/src/main/kotlin/com/edudesk/services/SupabaseAssignmentService.kt edudesk-kotlin/src/main/kotlin/com/edudesk/services/SupabaseCourseService.kt
git commit -m "refactor: Remove Supabase Assignment and Course services"

# 15
git rm -q edudesk-kotlin/src/main/kotlin/com/edudesk/services/SupabaseEnrollmentService.kt edudesk-kotlin/src/main/kotlin/com/edudesk/services/SupabaseUserService.kt
git commit -m "refactor: Remove Supabase Enrollment and User services"

# 16
git add edudesk-kotlin/src/main/kotlin/com/edudesk/models/Models.kt
git commit -m "refactor: Simplify Desktop data models for API communication"

# 17
git add edudesk-kotlin/src/main/kotlin/com/edudesk/services/AuthService.kt
git commit -m "refactor: Migrate AuthService to Ktor HTTP Client"

# 18
git add edudesk-kotlin/src/main/kotlin/com/edudesk/services/CourseService.kt edudesk-kotlin/src/main/kotlin/com/edudesk/services/AssignmentService.kt edudesk-kotlin/src/main/kotlin/com/edudesk/services/UserService.kt
git commit -m "refactor: Migrate Course, Assignment, and User services to HTTP Client"

# 19
git add edudesk-kotlin/src/main/kotlin/com/edudesk/ui/screens/LoginScreen.kt edudesk-kotlin/src/main/kotlin/com/edudesk/services/SessionManager.kt
git commit -m "refactor: Update LoginScreen and SessionManager to use new Auth system"

# 20
git add edudesk-kotlin/build.gradle.kts edudesk-kotlin/src/main/kotlin/com/edudesk/Main.kt
git rm -q edudesk-kotlin/.kotlin/sessions/kotlin-compiler-877654196780616692.salive || true
git commit -m "chore: Clean up unused SQLite and Supabase dependencies"

git push
