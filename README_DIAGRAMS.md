# 📖 Iels PlantUML Diagrams - Usage Guide

## 🎯 Overview

Berikut adalah ke-6 PlantUML diagram activity yang telah dibuat untuk mendokumentasikan Iels LMS secara detail:

---

## 📊 Diagram List & How to Use

### 1. **ACTIVITY_DIAGRAM_COMPLETE.puml**
**Deskripsi:** Comprehensive system flow dari app startup hingga dashboard untuk semua role.

**Isi:**
- System initialization (Database setup, Supabase connection)
- Authentication flow (Login, credential validation)
- Role-based routing (STUDENT → HomeScreen, INSTRUCTOR → Dashboard, ADMIN → Dashboard)
- 3 student paths (Course enrollment, Assignments, Learning content)
- Instructor & Admin workflows

**Gunakan untuk:**
- High-level system understanding
- Architecture overview presentations
- Onboarding new developers
- System design documentation

**Render:**
```bash
# Online
https://www.plantuml.com/plantuml/uml/paste-content-here

# Local (requires PlantUML)
plantuml ACTIVITY_DIAGRAM_COMPLETE.puml -o output.png
```

---

### 2. **ACTIVITY_DIAGRAM_AUTH.puml**
**Deskripsi:** Detailed authentication flow dengan sequence diagram & database interaction.

**Isi:**
- Login screen display
- Credential input & validation
- BCrypt password verification
- SessionManager setup
- Test users (4 default accounts)
- Role-based dashboard routing

**Gunakan untuk:**
- Understanding authentication architecture
- Security review & audit
- Debugging login issues
- Documenting test credentials

**Key Info:**
```
Default Test Users:
1. admin@iels.com / admin123 → ADMIN
2. instructor@iels.com / instructor123 → INSTRUCTOR
3. student@iels.com / student123 → STUDENT
4. student2@iels.com / student123 → STUDENT
```

---

### 3. **ACTIVITY_DIAGRAM_STUDENT.puml**
**Deskripsi:** Complete student learning workflow dari enrollment hingga assignment submission.

**Isi:**
- **Path 1: Course Enrollment** - Browse → Search → View Details → Enroll
- **Path 2: Assignment Management** - View → Select → Submit → Track Status
- **Path 3: Lesson Learning** - View Lessons → Learn Content → Mark Complete
- **Path 4: Progress Tracking** - Monitor grades & completion

**Database Operations:**
- Enrollments INSERT (when enrolling)
- Assignments SELECT with status filtering
- Submissions INSERT (when submitting work)
- UserCourses UPDATE (progress tracking)

**Gunakan untuk:**
- Student feature development
- User acceptance testing (UAT) scenarios
- Documentation untuk student users
- Workflow validation

---

### 4. **ACTIVITY_DIAGRAM_INSTRUCTOR.puml**
**Deskripsi:** Instructor class management, material upload, & grading workflow.

**Isi:**
- **Path 1: Class Management** - CRUD courses, enrollment management
- **Path 2: Material Upload** - Upload learning resources (PDF, Video, etc.)
- **Path 3: Grading** - Review submissions, enter grades, add feedback
- **Path 4: Class Reports** - Analytics, performance tracking

**Security Checks:**
- Instructor validates ownership (instructor_id check)
- Prevent deletion if students enrolled
- Grade records immutable after creation
- Notification sent to students

**Gunakan untuk:**
- Instructor feature implementation
- Grading system documentation
- Material management workflow
- Instructor onboarding

---

### 5. **ACTIVITY_DIAGRAM_ADMIN.puml**
**Deskripsi:** Admin system management, user lifecycle, & course approvals.

**Isi:**
- **Path 1: User Management** - CRUD users, role assignment, status control
- **Path 2: Course Approvals** - Review courses, approve/reject workflow
- **Path 3: System Reports** - Analytics, enrollment trends, revenue
- **Path 4: Configuration** - Settings, database config, API keys
- **Path 5: Audit Log** - Activity tracking & compliance

**Admin Capabilities:**
- Create/Edit/Delete users
- Change user roles & status
- Reset user passwords
- Approve/reject courses
- View system statistics (1,245 users, 86 courses)
- Generate compliance reports

**Gunakan untuk:**
- Admin feature development
- System administration documentation
- Compliance audit preparation
- Admin training materials

---

### 6. **DATABASE_SCHEMA_DIAGRAM.puml**
**Deskripsi:** Entity-relationship diagram (ERD) showing database schema & relationships.

**Entities:**
```
Users (10 fields)
  ├─ Courses (instructor_id)
  │   ├─ Enrollments
  │   ├─ Assignments
  │   ├─ Lessons
  │   └─ Materials
  ├─ Enrollments (user_id, course_id)
  ├─ Assignments (user_id, course_id)
  │   └─ Submissions
  ├─ Progress (user_id, course_id)
  ├─ Messages (sender_id, recipient_id)
  ├─ AuditLog (user_id)
  └─ Sessions (user_id)
```

**Key Relationships:**
- 1 User → Many Courses (as instructor)
- 1 User → Many Enrollments (as student)
- 1 Course → Many Assignments, Lessons, Materials
- 1 Assignment → Many Submissions

**Gunakan untuk:**
- Database design review
- Query planning & optimization
- Data modeling understanding
- Database migration planning

---

## 🎨 Color Coding

```
Activity Diagrams:
- 🟦 Blue: Student activities (HomeScreen, AssignmentsScreen)
- 🟧 Orange: Instructor activities (Dashboard, Grading)
- 🟪 Purple: Admin activities (User Management, Approvals)
- 🟩 Green: Success & completion states
- 🟥 Red: Errors & validations

Database Diagram:
- White entities: Primary data tables
- Blue connections: Foreign key relationships
- Yellow notes: Important constraints
```

---

## 📋 How to Export Diagrams

### Option 1: PlantUML Online Editor
```
1. Visit: https://www.plantuml.com/plantuml/uml/
2. Paste content dari .puml file
3. Select export format:
   - PNG (best for presentations)
   - SVG (best for websites)
   - PDF (best for documentation)
```

### Option 2: Local PlantUML CLI
```bash
# Installation
brew install plantuml  # macOS
sudo apt install plantuml  # Ubuntu/Linux

# Render single diagram
plantuml ACTIVITY_DIAGRAM_STUDENT.puml

# Render all diagrams
for file in *.puml; do plantuml "$file"; done

# Export to specific format
plantuml -tpng ACTIVITY_DIAGRAM_COMPLETE.puml
plantuml -tsvg ACTIVITY_DIAGRAM_COMPLETE.puml
```

### Option 3: VS Code Extension
```
1. Install: "PlantUML" extension (jjstq6)
2. Open .puml file
3. Press Alt+D to preview
4. Right-click → Export to PNG/SVG
```

---

## 🔍 Recommended Reading Order

**For New Developers:**
1. START: ACTIVITY_DIAGRAM_COMPLETE.puml (High-level overview)
2. THEN: DATABASE_SCHEMA_DIAGRAM.puml (Understanding data)
3. THEN: ACTIVITY_DIAGRAM_AUTH.puml (How to login)
4. FINALLY: Role-specific diagram (STUDENT/INSTRUCTOR/ADMIN)

**For QA/Testers:**
1. ACTIVITY_DIAGRAM_STUDENT.puml (Test student workflows)
2. ACTIVITY_DIAGRAM_INSTRUCTOR.puml (Test instructor workflows)
3. ACTIVITY_DIAGRAM_ADMIN.puml (Test admin workflows)

**For DevOps/DBA:**
1. DATABASE_SCHEMA_DIAGRAM.puml (Schema understanding)
2. CODEBASE_CROSS_CHECK_REPORT.md (Performance & scaling)

**For Security Audit:**
1. ACTIVITY_DIAGRAM_AUTH.puml (Security checks)
2. ACTIVITY_DIAGRAM_ADMIN.puml (Access control)
3. CODEBASE_CROSS_CHECK_REPORT.md (Security issues & recommendations)

---

## 💡 Usage Examples

### Example 1: Student Workflow Testing
```
1. Open ACTIVITY_DIAGRAM_STUDENT.puml
2. Identify Path 1: Course Enrollment
3. Test steps:
   - Login as student@iels.com
   - Navigate to CourseRegistrationScreen
   - Search for course
   - Click enroll
   - Verify in MyLearningScreen
```

### Example 2: Admin User Management
```
1. Open ACTIVITY_DIAGRAM_ADMIN.puml
2. Find Path 1: User Management
3. Implement requirement:
   - Add "Change Status" feature
   - Database: UPDATE Users SET status = ?
   - UI: Show confirmation dialog
   - Notification: Email to user
```

### Example 3: Database Optimization
```
1. Open DATABASE_SCHEMA_DIAGRAM.puml
2. Identify slow queries:
   - SELECT * FROM Users WHERE email = ?
   - SELECT * FROM Enrollments WHERE user_id = ?
3. Add indexes:
   - CREATE INDEX idx_users_email ON Users(email)
   - CREATE INDEX idx_enrollments_userid ON Enrollments(user_id)
4. Verify in CODEBASE_CROSS_CHECK_REPORT.md Priority 3
```

---

## ✅ Verification Checklist

Before deploying to production, verify:

- [ ] All diagrams match actual code implementation
- [ ] Database schema in code matches DATABASE_SCHEMA_DIAGRAM.puml
- [ ] All activity flows implemented correctly
- [ ] Error handling paths tested
- [ ] Role-based access working per diagrams
- [ ] Database relationships correct
- [ ] Audit logging implemented
- [ ] Performance tested per Cross-Check Report

---

## 📞 Troubleshooting

### Diagram won't render?
- Check: All syntax is valid PlantUML (@startuml ... @enduml)
- Solution: Use https://www.plantuml.com/plantuml/uml/ to debug

### Missing details?
- Read: CODEBASE_CROSS_CHECK_REPORT.md for detailed analysis
- Check: Code comments in respective .kt files
- Compare: Activity flow with actual service methods

### Workflow different than diagram?
- Update diagram if code changed
- OR update code if diagram is correct
- Keep both in sync for documentation accuracy

---

## 📈 Future Improvements

Additional diagrams to consider:
- [ ] Sequence diagram per service method
- [ ] State machine for assignment status
- [ ] C4 model for system context
- [ ] Data flow diagram (DFD) for Supabase sync
- [ ] Deployment architecture diagram
- [ ] API endpoint specification diagram

---

**Generated:** 2026-04-15  
**For:** Iels LMS Kotlin Project  
**Status:** Complete ✅

