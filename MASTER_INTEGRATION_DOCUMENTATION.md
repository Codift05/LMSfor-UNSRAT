# 🎯 EduDesk Master Integration Activity Diagram - Dokumentasi Profesional

**Versi:** 2.0 (Master Integration)  
**Status:** ✅ PRODUCTION READY  
**Tanggal:** April 15, 2026  
**Tingkat Kompleksitas:** EXPERT LEVEL  

---

## 📊 Ringkasan Eksekutif

Diagram **ACTIVITY_DIAGRAM_MASTER_INTEGRATION.puml** adalah representasi holistik dari seluruh sistem EduDesk LMS yang mengintegrasikan:

- ✅ System initialization dan database setup
- ✅ Authentication flow dengan BCrypt security
- ✅ Role-based routing (Student, Instructor, Admin)
- ✅ 3 parallel learning/management paths
- ✅ Common features untuk semua roles
- ✅ Error handling & security layers

**Uniqueness:** Diagram ini adalah SATU FILE yang menunjukkan SEMUA 6 diagrams sebelumnya dalam konteks terintegrasi.

---

## 🏗️ Struktur Diagram (6 Phases)

### **PHASE 1: System Initialization & Setup** 🚀
```
Duration: 1-5 seconds
Entry Point: Main.kt execution
```

**Aktivitas:**
1. Application startup dengan window creation (1280×800)
2. DatabaseSetup.init() - koneksi SQLite
3. Schema creation & 4 test users seeding
4. Supabase configuration (optional)
5. NavController routing ke LoginScreen

**Database Operations:**
```sql
-- Executed once on startup:
CREATE TABLE Users (...)
CREATE TABLE Courses (...)
CREATE TABLE Enrollments (...)
-- ... other tables

-- Seeding:
INSERT INTO Users VALUES (admin, instructor, student, student2)
```

**Key Components:**
- SQLite: `edudesk.db` (primary)
- Optional Supabase: Cloud backup
- Test data: Pre-populated for development

---

### **PHASE 2: Authentication & Session Management** 🔐

```
Duration: 5-10 seconds
Entry Point: LoginScreen display
```

**Authentication Flow:**

```
1. User Input
   ↓
2. AuthService.login(email_or_nim, password)
   ↓
3. Database Query
   SELECT * FROM Users WHERE email = ? OR nim = ?
   ↓
4. Password Verification
   BCrypt.verify(input_password, stored_hash)
   ↓
5a. SUCCESS: SessionManager.setLocalUser()
    ↓
    Store in memory: user, role, session_token
    ↓
    Optional: Sync to Supabase
    ↓
    Route to Dashboard
   
5b. ERROR: Display error, remain on LoginScreen
```

**Security Mechanisms:**
```kotlin
// Password verification (timing-safe)
BCrypt.verify(password, storedHash)

// Session creation
val session = Session(
    userId = user.id,
    token = generateSecureToken(),
    createdAt = Instant.now(),
    expiresAt = Instant.now().plus(24.hours)
)

// Optional Supabase sync
if (supabaseEnabled) {
    val jwtToken = supabaseClient.auth.login(email, password)
}
```

**Default Test Users:**
| Email | Password | Role |
|-------|----------|------|
| admin@edudesk.com | admin123 | ADMIN |
| instructor@edudesk.com | instructor123 | INSTRUCTOR |
| student@edudesk.com | student123 | STUDENT |
| student2@edudesk.com | student123 | STUDENT |

---

### **PHASE 3: Role-Based Routing & Dashboard Selection** 🔀

```
Execution Time: Instant (<100ms)
Condition: SessionManager.getCurrentUser().role
```

**Routing Logic:**

```kotlin
fun routeToDashboard(user: User) {
    when (user.role) {
        Role.STUDENT -> navController.navigate(HomeScreen::class)
        Role.INSTRUCTOR -> navController.navigate(InstructorDashboard::class)
        Role.ADMIN -> navController.navigate(AdminDashboard::class)
    }
}
```

**Dashboard Displays:**

| Role | Dashboard | Content | Permissions |
|------|-----------|---------|-------------|
| **STUDENT** | HomeScreen | 3 recent courses + assignments | View only |
| **INSTRUCTOR** | InstructorDashboard | 37 classes, 236 students | Manage own |
| **ADMIN** | AdminDashboard | 1,245 users, 86 courses | Full access |

---

### **PHASE 4A: Student Learning Path** 👨‍🎓

```
Duration: Ongoing (per student session)
Role: STUDENT
Access Level: Content consumer only
```

**Three Parallel Learning Paths:**

#### **4A.1: Course Enrollment Path**
```
HomeScreen 
  ↓ (Browse All Courses)
CourseRegistrationScreen
  ↓ (Search/Filter)
CourseDetailScreen
  ↓ (View syllabus)
Enroll Button
  ↓
INSERT Enrollments(user_id, course_id)
  ↓
MyLearningScreen
  ↓ (Track progress %)
```

**Database Operations:**
```sql
INSERT INTO Enrollments (user_id, course_id, enrolled_at)
VALUES (?, ?, NOW());

INSERT INTO UserCourses (user_id, course_id, enrollment_status)
VALUES (?, ?, 'ACTIVE');
```

#### **4A.2: Assignment Management Path**
```
HomeScreen
  ↓ (View Assignments)
AssignmentsScreen
  ↓ (Sorted by deadline)
Select Assignment
  ↓ (View details)
Submit Work
  ↓
INSERT Submissions(assignment_id, user_id, file_path)
  ↓
UPDATE Assignments.status = DONE
  ↓
✅ Confirmation with timestamp
```

**Submission Workflow:**
```sql
-- Student submits work
INSERT INTO Submissions (
    assignment_id, 
    user_id, 
    file_path, 
    submitted_at
) VALUES (?, ?, ?, NOW());

-- Mark assignment as done
UPDATE Assignments 
SET status = 'DONE' 
WHERE id = ?;
```

#### **4A.3: Lesson Learning Path**
```
HomeScreen
  ↓ (Click Lessons)
LessonScreen
  ↓ (View modules in sequence)
Watch Video / Read Material
  ↓
Mark Lesson Complete
  ↓
💾 Update progress % (real-time)
```

**Progress Tracking:**
```sql
UPDATE UserProgress 
SET lessons_completed = lessons_completed + 1,
    completion_percent = (lessons_completed / total_lessons) * 100,
    last_activity = NOW()
WHERE user_id = ? AND course_id = ?;
```

**Student Permissions Matrix:**
```
✅ Can: View enrolled courses, submit assignments, watch lessons, 
        see grades, track progress, message others
❌ Cannot: Manage courses, grade others, view all courses before enrolling,
          access admin features
```

---

### **PHASE 4B: Instructor Management Path** 👨‍🏫

```
Duration: Ongoing (per instructor session)
Role: INSTRUCTOR
Access Level: Own course management
```

**Four Parallel Management Paths:**

#### **4B.1: Class Management Path**
```
InstructorDashboard (37 Classes, 236 Students)
  ├─ Create Course → CourseCreationForm → INSERT Courses
  ├─ Edit Course → CourseEditForm → UPDATE Courses
  ├─ Delete Course → Confirm → DELETE Courses (if no students)
  └─ View Details → Display roster & submissions
```

**Course CRUD Operations:**
```sql
-- CREATE
INSERT INTO Courses (instructor_id, name, code, description, status)
VALUES (?, ?, ?, ?, 'PENDING');

-- READ
SELECT * FROM Courses WHERE instructor_id = ? AND status = 'ACTIVE';

-- UPDATE
UPDATE Courses SET name=?, description=? WHERE id=? AND instructor_id=?;

-- DELETE (only if no enrollments)
DELETE FROM Courses WHERE id=? AND instructor_id=?;
```

#### **4B.2: Material Upload Path**
```
InstructorDashboard
  ↓ (Upload Materials)
Select Course
  ↓
Choose File (PDF, Video, PPT, etc.)
  ↓
Validate: size ≤ 100MB, type allowed
  ↓
INSERT into Materials table
  ↓
✅ Available to students immediately
```

**File Storage:**
```sql
INSERT INTO Materials (
    course_id, 
    file_name, 
    file_path, 
    upload_date, 
    uploaded_by
) VALUES (?, ?, ?, NOW(), ?);
```

#### **4B.3: Grading Workflow**
```
InstructorDashboard
  ↓ (Grade Submissions)
Display Queue (sorted by deadline)
  ↓
Select Student Submission
  ↓
View Work (PDF, video, text)
  ↓
Score Against Rubric + Add Feedback
  ↓
UPDATE Submissions SET grade=?, feedback=?
  ↓
📧 Notify Student
  ↓
✅ Grade recorded
```

**Grading Database Operations:**
```sql
-- Record grade
UPDATE Submissions 
SET grade = ?, 
    feedback = ?, 
    graded_date = NOW(),
    status = 'GRADED'
WHERE id = ?;

-- Update course grade
UPDATE Enrollments 
SET grade = (
    SELECT AVG(grade) FROM Submissions 
    WHERE user_id = ? AND course_id = ?
)
WHERE user_id = ? AND course_id = ?;
```

#### **4B.4: Analytics Path**
```
InstructorDashboard
  ↓ (View Reports)
Display Charts:
  - Grade distribution histogram
  - Completion % pie chart
  - Enrollment trend line graph
  ↓
Export to CSV/PDF
```

**Instructor Permissions Matrix:**
```
✅ Can: Create/edit own courses, upload materials, grade submissions,
        view student roster, see analytics
❌ Cannot: Grade others' students, manage system, delete courses with students,
          edit other instructors' materials
```

---

### **PHASE 4C: Admin Oversight Path** ⚙️

```
Duration: Ongoing (per admin session)
Role: ADMIN
Access Level: Full system access
```

**Five Parallel Admin Paths:**

#### **4C.1: User Management Path**
```
AdminDashboard (1,245 users)
  ├─ View Profile → Display details & history
  ├─ Change Status → ACTIVE ↔ INACTIVE
  ├─ Reset Password → Generate temp + email
  ├─ Assign Role → STUDENT | INSTRUCTOR | ADMIN
  └─ Delete User → Confirm → CASCADE delete
```

**User Management Operations:**
```sql
-- Change status
UPDATE Users SET status = 'INACTIVE' WHERE id = ?;

-- Update role
UPDATE Users SET role = 'INSTRUCTOR' WHERE id = ?;

-- Reset password
UPDATE Users 
SET password_hash = BCrypt(temp_password),
    temp_password_set = TRUE,
    last_password_reset = NOW()
WHERE id = ?;

-- Delete user (careful!)
DELETE FROM Users WHERE id = ?;
```

#### **4C.2: Course Approvals Path**
```
AdminDashboard
  ↓ (Pending Course Approvals)
Display Queue
  ↓
Review Course Details
  ├─ ✅ APPROVE → status = 'ACTIVE'
  ├─ ❌ REJECT → request revisions
  └─ 📌 LATER → remains PENDING
  ↓
📧 Notify Instructor
```

**Approval Workflow:**
```sql
-- Approve course
UPDATE Courses SET status = 'ACTIVE' WHERE id = ?;

-- Reject course
UPDATE Courses 
SET status = 'REJECTED', 
    rejection_reason = ?
WHERE id = ?;
```

#### **4C.3: System Analytics Path**
```
AdminDashboard
  ↓ (Analytics & Reports)
Generate Insights:
  - User growth trend
  - Enrollment patterns
  - Course popularity
  - Revenue metrics
  ↓
Export reports (CSV/PDF)
```

#### **4C.4: System Configuration Path**
```
AdminDashboard
  ↓ (Settings)
Configure:
  - Supabase API keys
  - Database backup schedule
  - Email notification template
  - User role definitions
  ↓
✅ Save configuration
```

#### **4C.5: Audit Log Path**
```
AdminDashboard
  ↓ (Compliance)
Display Audit Trail:
  - Timestamp, user_id, action
  - Resource type & ID
  - Old value → new value
  - IP address (optional)
  ↓
Search, filter, export
```

**Audit Logging:**
```sql
INSERT INTO AuditLog (
    user_id, 
    timestamp, 
    action, 
    resource_type, 
    resource_id,
    old_value, 
    new_value, 
    ip_address
) VALUES (?, NOW(), ?, ?, ?, ?, ?, ?);
```

**Admin Permissions Matrix:**
```
✅ Can: Full system access, user CRUD, course approvals, 
        configuration, audit logs, reports
❌ Cannot: Nothing (unless restricted by policy)
```

---

### **PHASE 5: Common Features (All Roles)** 🌐

```
Duration: On-demand
Availability: All authenticated users
```

**Universal Features (Parallel Execution):**

#### **5.1: Profile Management**
```
User clicks "Profile"
  ↓
ProfileScreen
  - View: Name, NIM, email, role
  - Edit: Phone, address, picture
  ↓
UPDATE Users table
  ↓
✅ Profile updated
```

#### **5.2: Account Settings**
```
User clicks "Settings"
  ↓
AccountSettingsScreen
  - Change password (BCrypt)
  - Email preferences
  - Notifications toggle
  - Theme (Light/Dark)
  ↓
UPDATE UserPreferences
  ↓
✅ Settings saved
```

#### **5.3: Messages**
```
User clicks "Messages"
  ↓
MessagesScreen
  - View inbox
  - Send message to any user
  - Read/archive messages
  ↓
INSERT/SELECT from Messages table
  ↓
✅ Message tracking
```

#### **5.4: Help & Support**
```
User clicks "Help"
  ↓
HelpScreen
  - FAQs database
  - Documentation links
  - Support form
  ↓
Send to support email
```

#### **5.5: Logout**
```
User clicks "Logout"
  ↓
SessionManager.clearSession()
  ↓
DELETE FROM Sessions WHERE user_id = ?
  ↓
Clear memory state
  ↓
Route to LoginScreen
```

---

### **PHASE 6: Error Handling & Security** 🛡️

```
Timing: Continuous & on-demand
Scope: All operations
```

**Error Categories & Handling:**

#### **6.1: Network Errors** 🔴
```
Condition: Database connection failed
Response:
  1. Retry logic (max 3 attempts)
  2. Exponential backoff: 1s, 2s, 4s
  3. Fallback to offline mode (SQLite only)
  4. Queue operations for sync
```

#### **6.2: Validation Errors** ⚠️
```
Condition: Invalid input
Examples:
  - Email format invalid
  - NIM already exists
  - File type not allowed
  - Password too weak
Response:
  - Highlight field in UI
  - Display error message
  - Suggest correction
```

#### **6.3: Permission Errors** 🚫
```
Condition: Insufficient privileges
Examples:
  - Student trying to grade
  - Instructor accessing admin settings
  - User accessing others' data
Response:
  - Show "Access Denied"
  - Log unauthorized attempt
  - Alert admin (optional)
```

#### **6.4: Security Issues** ⛔
```
Implemented:
  - SQL injection prevention (parameterized queries)
  - Password reset enforcement (temp password)
  - Brute force detection (login attempt tracking)
  - Rate limiting (recommended for production)
  - IP-based access control (optional)
```

#### **6.5: File Upload Issues** 📤
```
Validation:
  1. File size ≤ 100MB
  2. Type in whitelist (PDF, MP4, PPTX, DOC)
  3. Virus scanning (optional)
  4. Quarantine suspicious files
Response:
  - Show error with reason
  - Allow retry or cancel
```

#### **6.6: Unexpected Errors** ❌
```
Response:
  1. Log to error tracking system (Sentry/ELK)
  2. Show generic error message to user
  3. Direct to support/help
  4. Generate error ID for troubleshooting
```

---

## 📐 System Architecture Overview

```
┌─────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                  │
│  (Jetpack Compose Screens - 13 total)                  │
├─────────────────────────────────────────────────────────┤
│  LoginScreen → HomeScreen / InstructorDashboard         │
│               / AdminDashboard                          │
│  ↓ (Navigation)                                         │
├─────────────────────────────────────────────────────────┤
│                   BUSINESS LOGIC LAYER                  │
│  (9 Services + SessionManager)                          │
├─────────────────────────────────────────────────────────┤
│  AuthService, UserService, CourseService, etc.          │
│  ↓ (Service calls)                                      │
├─────────────────────────────────────────────────────────┤
│                   DATA ACCESS LAYER                     │
│  (Exposed ORM + Kotlin Coroutines)                      │
├─────────────────────────────────────────────────────────┤
│  Database operations (SELECT, INSERT, UPDATE, DELETE)   │
│  ↓ (Query execution)                                    │
├─────────────────────────────────────────────────────────┤
│                    DATABASE LAYER                       │
│  SQLite (Local) + Supabase PostgreSQL (Cloud)          │
├─────────────────────────────────────────────────────────┤
│  Users | Courses | Enrollments | Assignments |          │
│  Submissions | Lessons | Materials | Progress | ...     │
└─────────────────────────────────────────────────────────┘
```

---

## 🔄 Data Flow Diagram

```
                        PHASE 1: Init
                            ↓
                  ┌─────────────────────┐
                  │  SQLite Connection  │
                  │  Schema Creation    │
                  │  Seed Test Users    │
                  └─────────────────────┘
                            ↓
                        PHASE 2: Auth
                            ↓
         ┌──────────────────────────────────────┐
         │  User Input (Email/NIM + Password)   │
         │           ↓                          │
         │  Query Users table                   │
         │  BCrypt password verification        │
         │           ↓                          │
         │  SessionManager.setLocalUser()       │
         │  (Optional Supabase sync)            │
         └──────────────────────────────────────┘
                            ↓
                        PHASE 3: Routing
                            ↓
         ┌──────────────────────────────────────┐
         │    Check user.role in SessionManager │
         │  ├─ STUDENT → HomeScreen             │
         │  ├─ INSTRUCTOR → InstructorDashboard │
         │  └─ ADMIN → AdminDashboard           │
         └──────────────────────────────────────┘
                            ↓
        ┌─────────────────────────────────────────────┐
        │              PHASE 4: Main Workflows         │
        ├─────────────────────────────────────────────┤
        │  ┌──────────┐  ┌──────────┐  ┌──────────┐  │
        │  │ Student  │  │Instructor│  │  Admin   │  │
        │  │ Learning │  │Management│  │Oversight │  │
        │  └──────────┘  └──────────┘  └──────────┘  │
        │       ↓              ↓              ↓       │
        │   Path A-C       Path A-D       Path A-E    │
        │  (Parallel)     (Parallel)     (Parallel)   │
        └─────────────────────────────────────────────┘
                            ↓
                    PHASE 5: Common
                            ↓
        ┌─────────────────────────────────────────────┐
        │ Profile | Settings | Messages | Help        │
        │         (Available to all roles)            │
        └─────────────────────────────────────────────┘
                            ↓
                    PHASE 6: Logging
                            ↓
         ┌──────────────────────────────────────┐
         │  All operations logged to AuditLog   │
         │  Error handling & security checks    │
         │  Continuous background monitoring    │
         └──────────────────────────────────────┘
```

---

## 💾 Database Entities Involved

| Phase | Tables Used | Operation Type |
|-------|-------------|-----------------|
| **Phase 1** | (NA) | N/A |
| **Phase 2** | Users | SELECT, BCrypt verify |
| **Phase 3** | Sessions | INSERT |
| **Phase 4A** | Courses, Enrollments, Assignments, Submissions, Progress | SELECT, INSERT, UPDATE |
| **Phase 4B** | Courses, Materials, Submissions, Assignments | SELECT, INSERT, UPDATE, DELETE |
| **Phase 4C** | Users, Courses, AuditLog | CRUD operations |
| **Phase 5** | UserPreferences, Messages, Sessions | CRUD operations |
| **Phase 6** | AuditLog, ErrorLog | INSERT |

---

## 🎨 Color Coding Legend

```
🔵 Blue (#2196F3)     = STUDENT activities
🟠 Orange (#FF6F00)   = INSTRUCTOR activities
🟣 Purple (#7B1FA2)   = ADMIN activities
🟢 Green (#00C853)    = SUCCESS states
🟡 Yellow (#FFC107)   = WARNING states
🔴 Red (#D32F2F)      = ERROR states
🩶 Gray (#E2E8EF)     = DATABASE & storage
```

---

## ✅ How to Use This Diagram

### For Developers
1. **Quick Onboarding:** Read PHASE 1-3 to understand system startup & auth
2. **Feature Development:** Focus on your role's PHASE 4 path
3. **Integration Testing:** Verify all phases work together
4. **Reference:** Keep open while coding business logic

### For QA/Testers
1. **Test Plan Creation:** Design test cases per phase
2. **User Story Mapping:** Map requirements to diagram steps
3. **Scenario Testing:** Simulate each path end-to-end
4. **Regression Testing:** Verify all paths after updates

### For DevOps
1. **Deployment Checklist:** Verify each phase ready for production
2. **Monitoring Points:** Set alerts at phase transitions
3. **Performance Baseline:** Benchmark each phase timing
4. **Disaster Recovery:** Plan failover for each phase

### For Security Audit
1. **Threat Modeling:** Identify risks at each phase
2. **Vulnerability Assessment:** Check security controls
3. **Compliance Verification:** Audit logging trail
4. **Penetration Testing:** Target weak points

---

## 📈 Performance Metrics

| Phase | Expected Duration | Critical Path | Bottleneck |
|-------|------------------|-----------------|-----------|
| 1 | 1-5 sec | DB Init | Schema creation |
| 2 | 5-10 sec | Auth | Password hashing |
| 3 | <100ms | Routing | Session lookup |
| 4A | Ongoing | Query | Course search |
| 4B | Ongoing | Upload | File I/O |
| 4C | Ongoing | Reporting | Aggregation |
| 5 | On-demand | Network | API calls |
| 6 | Background | Logging | Write speed |

---

## 🚀 Production Deployment Checklist

Before deploying, verify:

- [ ] Phase 1: Database backup strategy implemented
- [ ] Phase 2: Password policy enforced (min 8 chars, complexity)
- [ ] Phase 2: Rate limiting configured (max 5 login attempts/15min)
- [ ] Phase 3: Session timeout set (30 min inactivity)
- [ ] Phase 4A: Pagination implemented for large datasets
- [ ] Phase 4B: File upload validation enforced
- [ ] Phase 4C: Audit logging tested & working
- [ ] Phase 5: Message encryption enabled (optional)
- [ ] Phase 6: Error tracking (Sentry/ELK) configured
- [ ] All: SSL/TLS enabled for Supabase sync
- [ ] All: Input validation comprehensive
- [ ] All: Load testing passed (1000+ concurrent users)

---

## 📞 Support & Questions

**Q: How do I export this diagram?**  
A: Use PlantUML online editor: https://www.plantuml.com/plantuml/uml/

**Q: Can students see admin data?**  
A: No - role-based access control prevents unauthorized access

**Q: What if Supabase is down?**  
A: System continues in offline mode using SQLite only

**Q: How are passwords stored?**  
A: BCrypt hashing with cost factor ≥ 12

**Q: Is audit logging mandatory?**  
A: Yes - all sensitive operations logged (compliance)

---

**Generated:** 2026-04-15 | **For:** EduDesk LMS Project | **Status:** ✅ PRODUCTION READY

