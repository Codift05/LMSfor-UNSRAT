# Supabase Integration Guide

This document explains how to integrate Supabase with the EduDesk LMS application.

## Setup Instructions

### 1. Create Supabase Project

1. Go to [https://supabase.com](https://supabase.com)
2. Sign up or log in to your account
3. Create a new project
4. Wait for the project to initialize
5. Get your API credentials from the project settings

### 2. Configure Environment Variables

Copy `.env.example` to `.env` and update with your Supabase credentials:

```bash
cp .env.example .env
```

Edit `.env` and fill in:
```
SUPABASE_URL=https://your-project.supabase.co
SUPABASE_ANON_KEY=your_anon_key_here
SUPABASE_SERVICE_KEY=your_service_key_here
```

### 3. Create Supabase Tables

Run the following SQL in the Supabase SQL Editor:

```sql
-- Users table
CREATE TABLE users (
  id UUID PRIMARY KEY,
  nim VARCHAR(50) UNIQUE NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  name VARCHAR(255) NOT NULL,
  role VARCHAR(50) NOT NULL,
  avatar_url VARCHAR(500),
  created_at TIMESTAMP DEFAULT NOW()
);

-- Courses table
CREATE TABLE courses (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  code VARCHAR(50) UNIQUE NOT NULL,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  instructor_id UUID NOT NULL REFERENCES users(id),
  credits INT DEFAULT 3,
  created_at TIMESTAMP DEFAULT NOW()
);

-- Assignments table
CREATE TABLE assignments (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  course_id UUID NOT NULL REFERENCES courses(id),
  title VARCHAR(255) NOT NULL,
  description TEXT,
  due_date TIMESTAMP NOT NULL,
  created_at TIMESTAMP DEFAULT NOW()
);

-- Enrollments table
CREATE TABLE enrollments (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL REFERENCES users(id),
  course_id UUID NOT NULL REFERENCES courses(id),
  enrolled_at TIMESTAMP DEFAULT NOW(),
  status VARCHAR(50) DEFAULT 'active',
  UNIQUE(user_id, course_id)
);

-- Create indexes for faster queries
CREATE INDEX idx_courses_instructor ON courses(instructor_id);
CREATE INDEX idx_assignments_course ON assignments(course_id);
CREATE INDEX idx_enrollments_user ON enrollments(user_id);
CREATE INDEX idx_enrollments_course ON enrollments(course_id);
```

### 4. Enable Row Level Security (RLS)

Enable RLS policies for each table:

```sql
-- Enable RLS
ALTER TABLE users ENABLE ROW LEVEL SECURITY;
ALTER TABLE courses ENABLE ROW LEVEL SECURITY;
ALTER TABLE assignments ENABLE ROW LEVEL SECURITY;
ALTER TABLE enrollments ENABLE ROW LEVEL SECURITY;

-- Create basic policies (adjust based on your requirements)
CREATE POLICY "Users can view own data" ON users
  FOR SELECT USING (auth.uid() = id);

CREATE POLICY "Everyone can view courses" ON courses
  FOR SELECT USING (true);

CREATE POLICY "Everyone can view assignments" ON assignments
  FOR SELECT USING (true);

CREATE POLICY "Users can view their enrollments" ON enrollments
  FOR SELECT USING (auth.uid() = user_id OR EXISTS (
    SELECT 1 FROM courses WHERE id = enrollments.course_id 
    AND instructor_id = auth.uid()
  ));
```

## Usage in Code

### Authentication Service

```kotlin
val authService = AuthService()

// Local SQLite login
val user = authService.login("user@example.com", "password")

// Supabase login (async)
val supabaseUser = authService.loginWithSupabase("user@example.com", "password")
```

### User Service

```kotlin
val userService = SupabaseUserService()

// Get user by ID
val user = userService.getUserById("user-id")

// Get all instructors
val instructors = userService.getUsersByRole("instructor")

// Update user
userService.updateUser("user-id", mapOf("name" to "New Name"))
```

### Course Service

```kotlin
val courseService = SupabaseCourseService()

// Get all courses
val courses = courseService.getAllCourses()

// Get courses by instructor
val instructorCourses = courseService.getCoursesByInstructor("instructor-id")

// Create course
courseService.createCourse(mapOf(
    "id" to UUID.randomUUID().toString(),
    "code" to "CS101",
    "name" to "Introduction to Computer Science",
    "instructor_id" to "instructor-id"
))
```

### Assignment Service

```kotlin
val assignmentService = SupabaseAssignmentService()

// Get assignments by course
val assignments = assignmentService.getAssignmentsByCourse("course-id")

// Create assignment
assignmentService.createAssignment(mapOf(
    "id" to UUID.randomUUID().toString(),
    "course_id" to "course-id",
    "title" to "Assignment 1",
    "due_date" to LocalDateTime.now().plusDays(7).toString()
))
```

### Enrollment Service

```kotlin
val enrollmentService = SupabaseEnrollmentService()

// Get user's enrollments
val enrollments = enrollmentService.getEnrollmentsByUser("user-id")

// Enroll in course
enrollmentService.enrollUserInCourse("user-id", "course-id")

// Unenroll from course
enrollmentService.unenrollUserFromCourse("user-id", "course-id")
```

## Important Notes

- **Security**: Never commit `.env` file to version control. Add it to `.gitignore`
- **API Keys**: The `ANON_KEY` is safe to use in client-side code. The `SERVICE_KEY` should only be used server-side
- **Fallback**: The application falls back to local SQLite if Supabase is not configured
- **Async Operations**: All Supabase operations are asynchronous. Use `suspend` functions or coroutines

## Troubleshooting

### "Supabase initialization failed"
- Check if `.env` file exists and has correct values
- Verify internet connection
- Check Supabase project status

### "Connection timeout"
- Verify Supabase URL is correct
- Check API key is valid
- Ensure Row Level Security policies are configured

### "Authentication failed"
- Verify credentials are correct
- Check if user exists in database
- Ensure the user hasn't been deleted
