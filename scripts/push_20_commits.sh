#!/bin/bash

# 1
git add iels-backend/build.gradle.kts iels-backend/settings.gradle.kts
git commit -m "chore: Initialize iels-backend Ktor project"

# 2
git add iels-backend/src/main/kotlin/com/iels/backend/Application.kt
git commit -m "feat: Add Ktor Netty application entry point on port 8081"

# 3
git add iels-backend/src/main/kotlin/com/iels/backend/models/Users.kt iels-backend/src/main/kotlin/com/iels/backend/models/Courses.kt
git commit -m "feat: Define Users and Courses database schema"

# 4
git add iels-backend/src/main/kotlin/com/iels/backend/models/Enrollments.kt iels-backend/src/main/kotlin/com/iels/backend/models/Assignments.kt iels-backend/src/main/kotlin/com/iels/backend/models/Submissions.kt
git commit -m "feat: Define Enrollments, Assignments, and Submissions database schema"

# 5
git add iels-backend/src/main/kotlin/com/iels/backend/models/DatabaseFactory.kt
git commit -m "feat: Implement DatabaseFactory with Exposed ORM and HikariCP"

# 6
git add iels-backend/src/main/kotlin/com/iels/backend/routes/AuthRoutes.kt
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
git rm -q iels-kotlin/src/main/kotlin/com/iels/config/SupabaseClient.kt iels-kotlin/src/main/kotlin/com/iels/config/SupabaseConfig.kt
git commit -m "refactor: Remove Supabase configuration files"

# 12
git rm -q iels-kotlin/src/main/kotlin/com/iels/database/DatabaseSeeder.kt iels-kotlin/src/main/kotlin/com/iels/database/DatabaseSetup.kt
git commit -m "refactor: Remove SQLite and local database setup"

# 13
git rm -q iels-kotlin/src/main/kotlin/com/iels/models/SupabaseModels.kt
git commit -m "refactor: Remove Supabase-specific models"

# 14
git rm -q iels-kotlin/src/main/kotlin/com/iels/services/SupabaseAssignmentService.kt iels-kotlin/src/main/kotlin/com/iels/services/SupabaseCourseService.kt
git commit -m "refactor: Remove Supabase Assignment and Course services"

# 15
git rm -q iels-kotlin/src/main/kotlin/com/iels/services/SupabaseEnrollmentService.kt iels-kotlin/src/main/kotlin/com/iels/services/SupabaseUserService.kt
git commit -m "refactor: Remove Supabase Enrollment and User services"

# 16
git add iels-kotlin/src/main/kotlin/com/iels/models/Models.kt
git commit -m "refactor: Simplify Desktop data models for API communication"

# 17
git add iels-kotlin/src/main/kotlin/com/iels/services/AuthService.kt
git commit -m "refactor: Migrate AuthService to Ktor HTTP Client"

# 18
git add iels-kotlin/src/main/kotlin/com/iels/services/CourseService.kt iels-kotlin/src/main/kotlin/com/iels/services/AssignmentService.kt iels-kotlin/src/main/kotlin/com/iels/services/UserService.kt
git commit -m "refactor: Migrate Course, Assignment, and User services to HTTP Client"

# 19
git add iels-kotlin/src/main/kotlin/com/iels/ui/screens/LoginScreen.kt iels-kotlin/src/main/kotlin/com/iels/services/SessionManager.kt
git commit -m "refactor: Update LoginScreen and SessionManager to use new Auth system"

# 20
git add iels-kotlin/build.gradle.kts iels-kotlin/src/main/kotlin/com/iels/Main.kt
git rm -q iels-kotlin/.kotlin/sessions/kotlin-compiler-877654196780616692.salive || true
git commit -m "chore: Clean up unused SQLite and Supabase dependencies"

git push
